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
 * @since 2021-07-11 18:21:38
 */
@ApiModel(value = "com-pcy-domain-VerificationCodeSendRecord")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "verification_code_send_record")
public class VerificationCodeSendRecord {
    /**
     * 验证码短信id
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "验证码短信id")
    private Long id;

    /**
     * 发送目标电话号码
     */
    @TableField(value = "phone_number")
    @ApiModelProperty(value = "发送目标电话号码")
    private String phoneNumber;

    /**
     * 六位验证码
     */
    @TableField(value = "verification_code")
    @ApiModelProperty(value = "六位验证码")
    private String verificationCode;

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