package com.pcy.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 
 * @author PengChenyu
 * @since 2021-07-11 23:20:05
 */
@ApiModel(value="com-pcy-vo-MailTemplateVo")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MailTemplateVo {
    /**
     * 邮件模板id
     */
    @ApiModelProperty(value="邮件模板id")
    private Integer id;

    /**
     * 模板名
     */
    @ApiModelProperty(value="模板名")
    private String templateName;

    /**
     * 模板类型，0文字模板，1HTML模板
     */
    @ApiModelProperty(value="模板类型，0文字模板，1HTML模板")
    private String templateType;

    /**
     * 模板内容
     */
    @ApiModelProperty(value="模板内容")
    private String templateBody;

    /**
     * 是否可用，0不可以，1可用
     */
    @ApiModelProperty(value="是否可用，0不可以，1可用")
    private String status;


}