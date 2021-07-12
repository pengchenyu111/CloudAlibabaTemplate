package com.pcy.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author PengChenyu
 * @since 2021-07-11 23:20:05
 */
@ApiModel(value="com-pcy-domain-MailTemplate")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "mail_template")
public class MailTemplate {
    /**
     * 邮件模板id
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="邮件模板id")
    private Integer id;

    /**
     * 模板名
     */
    @TableField(value = "template_name")
    @ApiModelProperty(value="模板名")
    private String templateName;

    /**
     * 模板类型，0文字模板，1HTML模板
     */
    @TableField(value = "template_type")
    @ApiModelProperty(value="模板类型，0文字模板，1HTML模板")
    private String templateType;

    /**
     * 模板内容
     */
    @TableField(value = "template_body")
    @ApiModelProperty(value="模板内容")
    private String templateBody;

    /**
     * 是否可用，0不可以，1可用
     */
    @TableField(value = "status")
    @ApiModelProperty(value="是否可用，0不可以，1可用")
    private String status;

    /**
     * 创建人
     */
    @TableField(value = "create_by")
    @ApiModelProperty(value="创建人")
    private Long createBy;

    /**
     * 修改人
     */
    @TableField(value = "modify_by")
    @ApiModelProperty(value="修改人")
    private Long modifyBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 最后修改时间
     */
    @TableField(value = "last_update_time")
    @ApiModelProperty(value="最后修改时间")
    private Date lastUpdateTime;

}