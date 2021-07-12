package com.pcy.mq.service;

import cn.hutool.core.date.DateUtil;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.pcy.constant.SmsConstant;
import com.pcy.domain.VerificationCodeSendRecord;
import com.pcy.service.VerificationCodeSendRecordService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 短信服务
 *
 * @author PengChenyu
 * @since 2021-07-08 11:02:05
 */
@Service
@Slf4j
public class SmsService {

    @Autowired
    private VerificationCodeSendRecordService verificationCodeSendRecordService;

    /**
     * 发送验证码短信到用户
     *
     * @param phoneNumber 用户手机号
     * @return
     */
    @GlobalTransactional
    public boolean sendVerificationTo(String phoneNumber, String txId) throws Exception {
        // 发送验证码短信
        Client client = createClient(SmsConstant.ACCESS_KEY_ID, SmsConstant.ACCESS_KEY_SECRET);
        String verificationCode = generateVerifyCode();
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(phoneNumber)
                .setSignName(SmsConstant.SIGN_NAME)
                .setTemplateCode(SmsConstant.TEMPLATE_CODE)
                .setTemplateParam("{code:" + verificationCode + "}");
        SendSmsResponse sendSmsResponse = client.sendSms(sendSmsRequest);
        boolean isSuccess = "OK".equals(sendSmsResponse.getBody().getCode());
        log.info("目标用户 => {}，验证码 => {}，信息发送是否发送成功 => {}", phoneNumber, verificationCode, isSuccess);
        // 数据库存入记录
        VerificationCodeSendRecord record = VerificationCodeSendRecord.builder()
                .phoneNumber(phoneNumber)
                .verificationCode(verificationCode)
                .sendTime(DateUtil.parse(DateUtil.now()))
                .successFlag(isSuccess ? "1" : "0")
                .requestId(sendSmsResponse.getBody().getRequestId())
                .transactionId(txId)
                .build();
        boolean isStored = verificationCodeSendRecordService.save(record);
        return isSuccess && isStored;
    }

    /**
     * 使用AK&SK初始化账号Client
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @return Client
     * @throws Exception
     */
    private Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = SmsConstant.ENDPOINT;
        return new Client(config);
    }

    /**
     * 生成验证码
     *
     * @return 六位验证码
     */
    private String generateVerifyCode() {
        return String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
    }

}
