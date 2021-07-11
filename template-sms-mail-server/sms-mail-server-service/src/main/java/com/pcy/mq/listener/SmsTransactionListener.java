package com.pcy.mq.listener;

import com.pcy.constant.MQConstant;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;

/**
 * 短信事务消息监听器
 *
 * @author PengChenyu
 * @since 2021-07-11 09:38:45
 */
@RocketMQTransactionListener(txProducerGroup = MQConstant.SMS_TX_GROUP)
public class SmsTransactionListener implements RocketMQLocalTransactionListener {
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        System.out.println("啊啊啊111");
        return RocketMQLocalTransactionState.COMMIT;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        return null;
    }
}
