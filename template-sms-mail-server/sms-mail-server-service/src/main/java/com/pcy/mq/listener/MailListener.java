package com.pcy.mq.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcy.model.mail.MailMessage;
import com.pcy.mq.sink.MailSink;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 邮件消息监听器
 *
 * @author PengChenyu
 * @since 2021-07-11 10:26:43
 */
@Service
@Slf4j
public class MailListener {


    /**
     * 接收邮件事务消息
     *
     * @param message
     * @throws IOException
     */
    @StreamListener(value = MailSink.MAIL_INPUT)
    public void receiveTxMessage(String message) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        MailMessage mailMessage = objectMapper.readValue(message, MailMessage.class);
        log.info("该消息已被消费 => {}", mailMessage.toString());
    }
}
