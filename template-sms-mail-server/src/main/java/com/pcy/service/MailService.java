package com.pcy.service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.pcy.constant.MailConstant;
import com.pcy.model.mail.MailMessage;
import lombok.extern.slf4j.Slf4j;
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

    /**
     * 给单个用户发送邮件
     */
    public SingleSendMailResponse singleSendMailTo(MailMessage mailMessage) throws Exception {
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
        log.info("发件人 => {}，收件人 => {}，请求id => {}，事件id => {}",
                mailMessage.getAccountName(),
                mailMessage.getToAddress(),
                singleSendMailResponse.getRequestId(),
                singleSendMailResponse.getEnvId());
        return singleSendMailResponse;
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
