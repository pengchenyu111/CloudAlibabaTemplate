package com.pcy.model.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 邮件消息对象
 *
 * @author PengChenyu
 * @since 2021-07-10 13:55:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MailMessage implements Serializable {

    private static final long serialVersionUID = 6073041204087235924L;

    /**
     * 发信人
     */
    private String accountName;

    /**
     * 收信人
     */
    private String toAddress;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件标签
     */
    private String tagName;

    /**
     * 邮件HTML内容
     */
    private String mailHTMLBody;

    /**
     * 邮件text内容
     */
    private String mailTextBody;
}
