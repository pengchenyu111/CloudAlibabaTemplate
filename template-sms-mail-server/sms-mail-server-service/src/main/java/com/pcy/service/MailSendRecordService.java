package com.pcy.service;

import com.pcy.domain.MailSendRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author PengChenyu
 * @since 2021-07-10 15:40:51
 */
public interface MailSendRecordService extends IService<MailSendRecord> {


    /**
     * 根据事务id查询邮件记录
     *
     * @param txId 事务id
     * @return
     */
    MailSendRecord queryMailSendRecordByTxId(String txId);
}

