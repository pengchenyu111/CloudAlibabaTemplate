package com.pcy.feign;

import com.pcy.config.feign.OAuth2FeignConfig;
import com.pcy.domain.MailTemplate;
import com.pcy.model.ResponseObject;
import com.pcy.vo.MailTemplateVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author PengChenyu
 * @since 2021-07-12 21:51:17
 */
@FeignClient(
        name = "sms-mail-server-service",
        contextId = "mailTemplateFeign",
        configuration = OAuth2FeignConfig.class,
        path = "sms_mail/mail_template")
public interface MailTemplateFeign {

    /**
     * 邮件模板条件查询
     *
     * @param mailTemplateVo 查询条件
     * @return
     */
    @PostMapping()
    public ResponseObject<List<MailTemplate>> queryMailTemplate(@RequestBody MailTemplateVo mailTemplateVo);
}
