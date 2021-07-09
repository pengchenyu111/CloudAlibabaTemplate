package com.pcy.controller;

import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.pcy.constant.ErrorCodeMsg;
import com.pcy.model.ResponseObject;
import com.pcy.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author PengChenyu
 * @since 2021-07-08 11:45:16
 */
@RestController
@RequestMapping("sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @GetMapping("/{phoneNumber}")
    public ResponseObject<SendSmsResponse> send(@PathVariable("phoneNumber") String phoneNumber) throws Exception {
        SendSmsResponse sendSmsResponse = smsService.sendVerificationTo(phoneNumber);
        return ResponseObject.success(ErrorCodeMsg.OK_CODE, ErrorCodeMsg.OK_MESSAGE, sendSmsResponse);
    }

}
