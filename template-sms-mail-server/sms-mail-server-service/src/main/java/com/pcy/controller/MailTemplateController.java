package com.pcy.controller;

import com.pcy.constant.ErrorCodeMsg;
import com.pcy.domain.MailTemplate;
import com.pcy.model.ResponseObject;
import com.pcy.service.MailTemplateService;
import com.pcy.vo.MailTemplateVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author PengChenyu
 * @since 2021-07-12 21:28:49
 */
@RestController
@RequestMapping("sms_mail/mail_template")
@Api(value = "mail_template", tags = "邮件模板")
public class MailTemplateController {

    @Autowired
    private MailTemplateService mailTemplateService;

    @ApiOperation(value = "邮件模板条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mailTemplateVo", value = "查询条件")
    })
    @PostMapping()
    public ResponseObject<List<MailTemplate>> queryMailTemplate(@RequestBody MailTemplateVo mailTemplateVo) {
        List<MailTemplate> mailTemplateList = mailTemplateService.queryMailTemplate(mailTemplateVo);
        if (CollectionUtils.isEmpty(mailTemplateList)) {
            return ResponseObject.failed(ErrorCodeMsg.QUERY_NULL_CODE, ErrorCodeMsg.QUERY_SUCCESS_MESSAGE, null);
        }
        return ResponseObject.success(ErrorCodeMsg.QUERY_SUCCESS_CODE, ErrorCodeMsg.QUERY_SUCCESS_MESSAGE, mailTemplateList);
    }
}
