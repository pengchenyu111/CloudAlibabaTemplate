package com.pcy.service;

import com.pcy.domain.VerificationCodeSendRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author PengChenyu
 * @since 2021-07-10 15:40:51
 */
public interface VerificationCodeSendRecordService extends IService<VerificationCodeSendRecord> {


    /**
     * 根据事务id查询验证码短信
     *
     * @param txId 事务id
     * @return
     */
    VerificationCodeSendRecord queryRecordByTxId(String txId);
}

