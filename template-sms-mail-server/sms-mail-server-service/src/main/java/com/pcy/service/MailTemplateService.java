package com.pcy.service;

import com.pcy.domain.MailTemplate;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pcy.vo.MailTemplateVo;

import java.util.List;

/**
 * @author PengChenyu
 * @since 2021-07-11 23:20:05
 */
public interface MailTemplateService extends IService<MailTemplate> {


    /**
     * 邮件模板条件查询
     *
     * @param mailTemplateVo 查询条件
     */
    List<MailTemplate> queryMailTemplate(MailTemplateVo mailTemplateVo);
}
