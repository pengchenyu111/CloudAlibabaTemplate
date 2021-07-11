package com.pcy.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author PengChenyu
 * @since 2021-07-11 18:21:37
 */
@ApiModel(value = "com-pcy-domain-MailSendRecord")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "mail_send_record")
public class MailSendRecord {
    /**
     * 邮件id
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "邮件id")
    private Long id;

    /**
     * 发信地址
     */
    @TableField(value = "account_name")
    @ApiModelProperty(value = "发信地址")
    private String accountName;

    /**
     * 目标收件人地址
     */
    @TableField(value = "to_address")
    @ApiModelProperty(value = "目标收件人地址")
    private String toAddress;

    /**
     * 邮件主题
     */
    @TableField(value = "subject")
    @ApiModelProperty(value = "邮件主题")
    private String subject;

    /**
     * 邮件标签
     */
    @TableField(value = "tag_name")
    @ApiModelProperty(value = "邮件标签")
    private String tagName;

    /**
     * 邮件 html 正文，限制28K，即最多9557个汉字
     */
    @TableField(value = "mail_html_body")
    @ApiModelProperty(value = "邮件 html 正文，限制28K，即最多9557个汉字")
    private String mailHtmlBody;

    /**
     * 邮件 text 正文，限制28K，即最多9557个汉字
     */
    @TableField(value = "mail_text_body")
    @ApiModelProperty(value = "邮件 text 正文，限制28K，即最多9557个汉字")
    private String mailTextBody;

    /**
     * 发送时间
     */
    @TableField(value = "send_time")
    @ApiModelProperty(value = "发送时间")
    private Date sendTime;

    /**
     * 发送是否成功标志，1成功，0失败
     */
    @TableField(value = "success_flag")
    @ApiModelProperty(value = "发送是否成功标志，1成功，0失败")
    private String successFlag;

    /**
     * 阿里云请求id
     */
    @TableField(value = "request_id")
    @ApiModelProperty(value = "阿里云请求id")
    private String requestId;

    /**
     * 事务id
     */
    @TableField(value = "transaction_id")
    @ApiModelProperty(value = "事务id")
    private String transactionId;

}