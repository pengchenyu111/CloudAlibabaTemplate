package com.pcy.controller;

import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.pcy.constant.ErrorCodeMsg;
import com.pcy.constant.MQConstant;
import com.pcy.model.ResponseObject;
import com.pcy.mq.service.SmsService;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author PengChenyu
 * @since 2021-07-08 11:45:16
 */
@RestController
@RequestMapping("sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("/{phoneNumber}")
    public ResponseObject<TransactionSendResult> send(@PathVariable("phoneNumber") String phoneNumber) throws Exception {
        String txId = UUID.randomUUID().toString();
        Message<String> message = MessageBuilder.withPayload(phoneNumber)
                .setHeader(RocketMQHeaders.TRANSACTION_ID, txId)
                .build();
        TransactionSendResult transactionSendResult = rocketMQTemplate.sendMessageInTransaction(MQConstant.SMS_TX_GROUP, "sms-topic", message, null);
        return ResponseObject.success(ErrorCodeMsg.OK_CODE, ErrorCodeMsg.OK_MESSAGE, transactionSendResult);
    }

}
