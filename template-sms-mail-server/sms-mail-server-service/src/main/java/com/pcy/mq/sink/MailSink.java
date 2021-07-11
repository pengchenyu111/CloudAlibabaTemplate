package com.pcy.mq.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 邮箱消费channel
 *
 * @author PengChenyu
 * @since 2021-07-10 16:17:16
 */
public interface MailSink {

    String MAIL_INPUT = "mail-input";

    @Input(MailSink.MAIL_INPUT)
    SubscribableChannel mailInput();
}
