package com.pcy.mq.listener;


import com.aliyuncs.exceptions.ClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcy.constant.MQConstant;
import com.pcy.domain.MailSendRecord;
import com.pcy.model.mail.MailMessage;
import com.pcy.mq.service.MailService;
import com.pcy.service.MailSendRecordService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

/**
 * 邮件事务消息监听器
 *
 * @author PengChenyu
 * @since 2021-07-11 09:38:58
 */
@Slf4j
@RocketMQTransactionListener(txProducerGroup = MQConstant.MAIL_TX_GROUP)
public class MailTransactionListener implements RocketMQLocalTransactionListener {

    @Autowired
    private MailService mailService;

    @Autowired
    private MailSendRecordService mailSendRecordService;

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        try {
            String msg = new String((byte[]) message.getPayload());
            ObjectMapper objectMapper = new ObjectMapper();
            MailMessage mailMessage = objectMapper.readValue(msg, MailMessage.class);
            String txId = (String) message.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);
            boolean flag = mailService.singleSendMailTo(mailMessage, txId);
            return flag ? RocketMQLocalTransactionState.COMMIT : RocketMQLocalTransactionState.ROLLBACK;
        } catch (JsonProcessingException e) {
            log.info("Json转换出错，msg => {}", e.getMessage());
            return RocketMQLocalTransactionState.ROLLBACK;
        } catch (ClientException e) {
            log.info("邮件接口调用出错，requestId => {}，errCode => {}，errMsg => {}，errorDescription => {}",
                    e.getRequestId(), e.getErrCode(), e.getErrMsg(), e.getErrorDescription());
            return RocketMQLocalTransactionState.ROLLBACK;
        } catch (Exception e) {
            log.info("msg => {}", e.getMessage());
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        String txId = (String) message.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);
        log.info("检查事务id => {}", txId);
        MailSendRecord record = mailSendRecordService.queryMailSendRecordByTxId(txId);
        return record == null ? RocketMQLocalTransactionState.ROLLBACK : RocketMQLocalTransactionState.COMMIT;
    }
}
