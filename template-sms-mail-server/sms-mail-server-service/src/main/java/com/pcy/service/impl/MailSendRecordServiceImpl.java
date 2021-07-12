package com.pcy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pcy.vo.MailSendRecordVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    @Autowired
    private MailSendRecordMapper mailSendRecordMapper;

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

    /**
     * 邮件发送记录分页查询
     *
     * @param current          当前页
     * @param size             每页大小
     * @param mailSendRecordVo 查询条件
     * @return
     */
    @Override
    public Page<MailSendRecord> queryMailSendRecordPage(int current, int size, MailSendRecordVo mailSendRecordVo) {
        Page<MailSendRecord> page = new Page<>(current, size);
        return mailSendRecordMapper.selectPage(
                page,
                new LambdaQueryWrapper<MailSendRecord>()
                        .eq(mailSendRecordVo.getId() != null, MailSendRecord::getId, mailSendRecordVo.getId())
                        .like(!StringUtils.isEmpty(mailSendRecordVo.getAccountName()), MailSendRecord::getAccountName, mailSendRecordVo.getAccountName())
                        .like(!StringUtils.isEmpty(mailSendRecordVo.getToAddress()), MailSendRecord::getToAddress, mailSendRecordVo.getToAddress())
                        .like(!StringUtils.isEmpty(mailSendRecordVo.getSubject()), MailSendRecord::getSubject, mailSendRecordVo.getSubject())
                        .like(!StringUtils.isEmpty(mailSendRecordVo.getTagName()), MailSendRecord::getTagName, mailSendRecordVo.getTagName())
                        .ge(mailSendRecordVo.getStartTime() != null, MailSendRecord::getSendTime, mailSendRecordVo.getStartTime())
                        .le(mailSendRecordVo.getEndTime() != null, MailSendRecord::getSendTime, mailSendRecordVo.getEndTime())
                        .eq(!StringUtils.isEmpty(mailSendRecordVo.getSuccessFlag()), MailSendRecord::getSuccessFlag, mailSendRecordVo.getSuccessFlag())
                        .eq(!StringUtils.isEmpty(mailSendRecordVo.getRequestId()), MailSendRecord::getRequestId, mailSendRecordVo.getRequestId())
        );
    }
}

