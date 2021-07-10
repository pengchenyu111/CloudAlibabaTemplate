package com.pcy.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pcy.domain.MailSendRecord;
import com.pcy.mapper.MailSendRecordMapper;
import com.pcy.service.MailSendRecordService;

/**
 * @author PengChenyu
 * @since 2021-07-10 15:40:51
 */
@Service
public class MailSendRecordServiceImpl extends ServiceImpl<MailSendRecordMapper, MailSendRecord> implements MailSendRecordService {

}

