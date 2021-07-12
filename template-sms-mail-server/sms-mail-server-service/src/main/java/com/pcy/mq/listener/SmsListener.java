package com.pcy.mq.listener;

import com.pcy.mq.sink.SmsSink;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 短息消息监听器
 *
 * @author PengChenyu
 * @since 2021-07-12 15:07:30
 */
@Service
@Slf4j
public class SmsListener {

    /**
     * 接收短信事务消息
     *
     * @param message
     * @throws IOException
     */
    @StreamListener(value = SmsSink.SMS_INPUT)
    public void receiveTxMessage(String message) throws IOException {

        log.info("发送给用户 => {} 短信，该消息已被消费", message);
    }
}
