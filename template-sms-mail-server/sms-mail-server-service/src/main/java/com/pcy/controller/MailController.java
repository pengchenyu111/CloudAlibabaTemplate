package com.pcy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcy.constant.ErrorCodeMsg;
import com.pcy.constant.MQConstant;
import com.pcy.model.ResponseObject;
import com.pcy.model.mail.MailMessage;
import com.pcy.mq.service.MailService;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author PengChenyu
 * @since 2021-07-10 14:26:27
 */
@RestController
@RequestMapping("mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @PostMapping()
    public ResponseObject<TransactionSendResult> send(@RequestBody MailMessage mailMessage) throws Exception {
        System.out.println(mailMessage.toString());
        System.out.println("*************");
        // 下面这些代码应当放在某个服务的service里
        // 这里为了测试方面就直接写在controller里
        String txId = UUID.randomUUID().toString();
        ObjectMapper objectMapper = new ObjectMapper();
        String mailMessageStr = objectMapper.writeValueAsString(mailMessage);
        Message message = MessageBuilder.withPayload(mailMessageStr)
                .setHeader(RocketMQHeaders.TRANSACTION_ID, txId)
                .build();
        TransactionSendResult transactionSendResult = rocketMQTemplate.sendMessageInTransaction(MQConstant.MAIL_TX_GROUP, "mail-topic", message, null);
        return ResponseObject.success(ErrorCodeMsg.OK_CODE, ErrorCodeMsg.OK_MESSAGE, transactionSendResult);
    }
}
