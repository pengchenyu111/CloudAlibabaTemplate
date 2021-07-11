package com.pcy.constant;

/**
 * 消息常量
 *
 * @author PengChenyu
 * @since 2021-07-11 09:55:38
 */
public class MQConstant {

    // 以下为RocketMQ事务组
    /**
     * 短信服务事务组
     */
    public static final String SMS_TX_GROUP = "SmsTransactionGroup";

    /**
     * 邮件服务事务组
     */
    public static final String MAIL_TX_GROUP = "MailTransactionGroup";
}
