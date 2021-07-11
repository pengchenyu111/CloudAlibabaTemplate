package com.pcy.mq.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 短信消费channel
 *
 * @author PengChenyu
 * @since 2021-07-10 16:17:01
 */
public interface SmsSink {

    String SMS_INPUT = "sms-input";

    @Input(SmsSink.SMS_INPUT)
    SubscribableChannel smsInput();
}
