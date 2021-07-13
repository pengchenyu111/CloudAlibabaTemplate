package com.pcy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pcy.vo.MailTemplateVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pcy.mapper.MailTemplateMapper;
import com.pcy.domain.MailTemplate;
import com.pcy.service.MailTemplateService;

/**
 * @author PengChenyu
 * @since 2021-07-11 23:20:05
 */
@Service
public class MailTemplateServiceImpl extends ServiceImpl<MailTemplateMapper, MailTemplate> implements MailTemplateService {

    @Autowired
    private MailTemplateMapper mailTemplateMapper;

    /**
     * 邮件模板条件查询
     *
     * @param mailTemplateVo 查询条件
     */
    @Override
    public List<MailTemplate>  queryMailTemplate(MailTemplateVo mailTemplateVo) {
        return mailTemplateMapper.selectList(new LambdaQueryWrapper<MailTemplate>()
                .eq(mailTemplateVo.getId() != null, MailTemplate::getId, mailTemplateVo.getId())
                .like(!StringUtils.isEmpty(mailTemplateVo.getTemplateName()), MailTemplate::getTemplateName, mailTemplateVo.getTemplateName())
                .eq(!StringUtils.isEmpty(mailTemplateVo.getTemplateType()), MailTemplate::getTemplateType, mailTemplateVo.getTemplateType())
                .like(!StringUtils.isEmpty(mailTemplateVo.getTemplateBody()), MailTemplate::getTemplateBody, mailTemplateVo.getTemplateBody())
                .eq(!StringUtils.isEmpty(mailTemplateVo.getStatus()), MailTemplate::getStatus, mailTemplateVo.getStatus())
        );
    }
}
