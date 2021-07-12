package com.pcy.mq.listener;

import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.pcy.constant.MQConstant;
import com.pcy.domain.VerificationCodeSendRecord;
import com.pcy.mq.service.SmsService;
import com.pcy.service.VerificationCodeSendRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

/**
 * 短信事务消息监听器
 *
 * @author PengChenyu
 * @since 2021-07-11 09:38:45
 */
@Slf4j
@RocketMQTransactionListener(txProducerGroup = MQConstant.SMS_TX_GROUP)
public class SmsTransactionListener implements RocketMQLocalTransactionListener {

    @Autowired
    private SmsService smsService;

    @Autowired
    private VerificationCodeSendRecordService verificationCodeSendRecordService;

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        String txId = (String) message.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);
        String phoneNumber = new String((byte[]) message.getPayload());
        log.info("短信消息来临 => {}", phoneNumber);
        try {
            boolean flag = smsService.sendVerificationTo(phoneNumber, txId);
            return flag ? RocketMQLocalTransactionState.COMMIT : RocketMQLocalTransactionState.ROLLBACK;
        } catch (Exception e) {
            log.info("短信接口调用失败，手机号 => {}，错误信息 => {}", phoneNumber, e.getMessage());
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        String txId = (String) message.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);
        VerificationCodeSendRecord record = verificationCodeSendRecordService.queryRecordByTxId(txId);
        return record == null ? RocketMQLocalTransactionState.ROLLBACK : RocketMQLocalTransactionState.COMMIT;
    }
}
