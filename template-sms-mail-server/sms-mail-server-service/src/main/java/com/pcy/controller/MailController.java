package com.pcy.controller;

import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.pcy.constant.ErrorCodeMsg;
import com.pcy.model.ResponseObject;
import com.pcy.model.mail.MailMessage;
import com.pcy.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author PengChenyu
 * @since 2021-07-10 14:26:27
 */
@RestController
@RequestMapping("mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping()
    public ResponseObject<SingleSendMailResponse> send(@RequestBody MailMessage mailMessage) throws Exception {
        System.out.println(mailMessage.toString());
        return ResponseObject.success(ErrorCodeMsg.OK_CODE, ErrorCodeMsg.OK_MESSAGE, mailService.singleSendMailTo(mailMessage));
    }
}
