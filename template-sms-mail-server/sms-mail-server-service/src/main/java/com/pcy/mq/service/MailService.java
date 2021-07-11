package com.pcy.mq.service;

import cn.hutool.core.date.DateUtil;
import com.aliyuncs.AcsResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.pcy.constant.MailConstant;
import com.pcy.domain.MailSendRecord;
import com.pcy.model.mail.MailMessage;
import com.pcy.service.MailSendRecordService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 邮件服务
 *
 * @author PengChenyu
 * @since 2021-07-09 21:13:29
 */
@Service
@Slf4j
public class MailService {

    @Autowired
    private MailSendRecordService mailSendRecordService;

    /**
     * 给单个用户发送邮件
     *
     * @param mailMessage 邮件内容
     * @param txId        事务id
     */
    @GlobalTransactional
    public boolean singleSendMailTo(MailMessage mailMessage, String txId) throws Exception {
        MailSendRecord record = singleSendMail(mailMessage, txId);
        boolean isStore = storeMailRecord(record);
        return record != null && isStore;
    }

    /**
     * 阿里云发送单个邮件接口
     *
     * @param mailMessage
     * @return
     * @throws Exception
     */
    private MailSendRecord singleSendMail(MailMessage mailMessage, String txId) throws Exception {
        IAcsClient client = createClient(MailConstant.REGION, MailConstant.ACCESS_KEY_ID, MailConstant.ACCESS_KEY_SECRET);
        SingleSendMailRequest request = new SingleSendMailRequest();
        // 发信地址
        request.setAccountName(mailMessage.getAccountName());
        // 0：为随机账号 1：为发信地址
        request.setAddressType(1);
        // 邮件标签，和阿里云上保持一致
        request.setTagName(mailMessage.getTagName());
        // 是否启用管理控制台中配置好回信地址（状态须验证通过），取值范围是字符串true或者false
        request.setReplyToAddress(true);
        // 目标地址
        request.setToAddress(mailMessage.getToAddress());
        // 邮件主题
        request.setSubject(mailMessage.getSubject());
        //如果采用byte[].toString的方式的话请确保最终转换成utf-8的格式再放入htmlbody和textbody，若编码不一致则会被当成垃圾邮件。
        //注意：文本邮件的大小限制为3M，过大的文本会导致连接超时或413错误
        request.setHtmlBody(mailMessage.getMailHTMLBody());
        request.setTextBody(mailMessage.getMailTextBody());
        // 调用阿里云接口发送邮件
        SingleSendMailResponse singleSendMailResponse = client.getAcsResponse(request);
        log.info("发件人 => {}，收件人 => {}，请求id => {}", mailMessage.getAccountName(), mailMessage.getToAddress(), singleSendMailResponse.getRequestId());
        // 装配返回对象
        MailSendRecord record = MailSendRecord.builder()
                .accountName(mailMessage.getAccountName())
                .toAddress(mailMessage.getToAddress())
                .subject(mailMessage.getSubject())
                .tagName(mailMessage.getTagName())
                .mailHtmlBody(mailMessage.getMailHTMLBody())
                .mailTextBody(mailMessage.getMailTextBody())
                .sendTime(DateUtil.parse(DateUtil.now()))
                .successFlag(singleSendMailResponse.getEnvId() == null ? "0" : "1")
                .requestId(singleSendMailResponse.getRequestId())
                .transactionId(txId)
                .build();
        return record;
    }

    /**
     * 将邮件发送结果存入数据库
     *
     * @param record
     * @return
     */
    private boolean storeMailRecord(MailSendRecord record) {
        return mailSendRecordService.save(record);
    }


    /**
     * 使用AK&SK初始化账号Client
     *
     * @param region
     * @param accessKeyId
     * @param accessKeySecret
     * @return Client
     * @throws Exception
     */
    private IAcsClient createClient(String region, String accessKeyId, String accessKeySecret) throws Exception {
        IClientProfile profile = DefaultProfile.getProfile(region, accessKeyId, accessKeySecret);
        return new DefaultAcsClient(profile);
    }

}
