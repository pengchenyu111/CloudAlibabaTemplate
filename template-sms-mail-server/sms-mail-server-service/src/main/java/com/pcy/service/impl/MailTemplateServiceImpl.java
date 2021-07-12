package com.pcy.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pcy.mapper.MailTemplateMapper;
import com.pcy.domain.MailTemplate;
import com.pcy.service.MailTemplateService;
/**
 * 
 * @author PengChenyu
 * @since 2021-07-11 23:20:05
 */
@Service
public class MailTemplateServiceImpl extends ServiceImpl<MailTemplateMapper, MailTemplate> implements MailTemplateService{

}
