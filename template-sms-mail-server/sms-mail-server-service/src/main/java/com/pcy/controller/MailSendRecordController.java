package com.pcy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pcy.constant.ErrorCodeMsg;
import com.pcy.domain.MailSendRecord;
import com.pcy.model.ResponseObject;
import com.pcy.service.MailSendRecordService;
import com.pcy.vo.MailSendRecordVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author PengChenyu
 * @since 2021-07-12 16:39:04
 */
@RestController
@RequestMapping("sms_mail/mail_record")
@Api(value = "mail_record", tags = "邮件发送记录详情")
public class MailSendRecordController {

    @Autowired
    private MailSendRecordService mailSendRecordService;

    @ApiOperation(value = "邮件发送记录分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页"),
            @ApiImplicitParam(name = "size", value = "每页大小"),
            @ApiImplicitParam(name = "mailSendRecordVo", value = "查询条件"),
    })
    @PostMapping("/page/current/{current}/size/{size}")
    public ResponseObject<Page<MailSendRecord>> queryPage(@PathVariable("current") int current, @PathVariable("size") int size, @RequestBody MailSendRecordVo mailSendRecordVo) {
        Page<MailSendRecord> mailSendRecordPage = mailSendRecordService.queryMailSendRecordPage(current, size, mailSendRecordVo);
        if (mailSendRecordPage.getTotal() == 0) {
            return ResponseObject.failed(ErrorCodeMsg.QUERY_NULL_CODE, ErrorCodeMsg.QUERY_NULL_MESSAGE, null);
        }
        return ResponseObject.success(ErrorCodeMsg.QUERY_SUCCESS_CODE, ErrorCodeMsg.QUERY_SUCCESS_MESSAGE, mailSendRecordPage);
    }
}
