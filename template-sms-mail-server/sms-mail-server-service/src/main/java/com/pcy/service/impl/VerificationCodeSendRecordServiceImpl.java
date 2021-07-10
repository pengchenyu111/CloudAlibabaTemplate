package com.pcy.service.impl;

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

}

