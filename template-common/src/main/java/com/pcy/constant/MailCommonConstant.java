package com.pcy.constant;

/**
 * @author PengChenyu
 * @since 2021-07-10 14:09:32
 */
public class MailCommonConstant {

    /**
     * 发信地址，和阿里云上保持一致
     * 触发类邮件指注册激活、密码找回等；批量邮件指营销推广、订阅期刊等；
     */
    public static final String ACCOUNT_NAME_TRIGGER = "datacenter@mail.happycoder.ltd";
    public static final String ACCOUNT_NAME_BATCH = "datacube@mail.happycoder.ltd";

    /**
     * 邮件标签，和阿里云上保持一致
     * Subscription：订阅服务
     * Promotion：推广服务
     * Activate：激活
     * ChangePassword：修改密码
     * Register：注册
     */
    public static final String TAG_SUBSCRIPTION = "Subscription";
    public static final String TAG_PROMOTION = "Promotion";
    public static final String TAG_ACTIVATE = "Activate";
    public static final String TAG_CHANGE_PASSWORD = "ChangePassword";
    public static final String TAG_REGISTER = "Register";
}
