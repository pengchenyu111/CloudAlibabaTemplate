package com.pcy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pcy.domain.MailSendRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pcy.vo.MailSendRecordVo;

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

    /**
     * 邮件发送记录分页查询
     *
     * @param current          当前页
     * @param size             每页大小
     * @param mailSendRecordVo 查询条件
     * @return
     */
    Page<MailSendRecord> queryMailSendRecordPage(int current, int size, MailSendRecordVo mailSendRecordVo);
}

