package com.pcy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pcy.mapper.VerificationCodeSendRecordMapper;
import com.pcy.domain.VerificationCodeSendRecord;
import com.pcy.service.VerificationCodeSendRecordService;

/**
 * @author PengChenyu
 * @since 2021-07-10 15:40:51
 */
@Service
public class VerificationCodeSendRecordServiceImpl extends ServiceImpl<VerificationCodeSendRecordMapper, VerificationCodeSendRecord> implements VerificationCodeSendRecordService {

    /**
     * 根据事务id查询验证码短信
     *
     * @param txId 事务id
     * @return
     */
    @Override
    public VerificationCodeSendRecord queryRecordByTxId(String txId) {
        return this.getOne(new LambdaQueryWrapper<VerificationCodeSendRecord>()
                .eq(!StringUtils.isEmpty(txId), VerificationCodeSendRecord::getTransactionId, txId));
    }
}

