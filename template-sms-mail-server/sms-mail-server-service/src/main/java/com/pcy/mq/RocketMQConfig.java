package com.pcy.mq;

import com.pcy.mq.sink.MailSink;
import com.pcy.mq.sink.SmsSink;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

/**
 * RocketMQ配置，配置Source和Sink
 *
 * @author PengChenyu
 * @since 2021-07-10 16:14:33
 */
@Configuration
@EnableBinding({SmsSink.class, MailSink.class})
public class RocketMQConfig {
}
