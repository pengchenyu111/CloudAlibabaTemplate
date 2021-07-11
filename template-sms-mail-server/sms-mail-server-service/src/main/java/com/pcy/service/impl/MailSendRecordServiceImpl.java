package com.pcy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * 根据事务id查询邮件记录
     *
     * @param txId 事务id
     * @return
     */
    @Override
    public MailSendRecord queryMailSendRecordByTxId(String txId) {
        return this.getOne(new LambdaQueryWrapper<MailSendRecord>()
                .eq(!StringUtils.isEmpty(txId), MailSendRecord::getTransactionId, txId));
    }
}

