package com.pcy.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author PengChenyu
 * @since 2021-07-01 20:43:24
 */
@ApiModel(value="com-pcy-domain-MovieUser")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "movie_user")
public class MovieUser {
    /**
     * 用户id
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    @ApiModelProperty(value="用户id")
    private Integer userId;

    /**
     * 用户名
     */
    @TableField(value = "user_name")
    @ApiModelProperty(value="用户名")
    private String userName;

    /**
     * 用户唯一名字标志，短评上没有id，以此做唯一标识
     */
    @TableField(value = "user_unique_name")
    @ApiModelProperty(value="用户唯一名字标志，短评上没有id，以此做唯一标识")
    private String userUniqueName;

    /**
     * 用户账户
     */
    @TableField(value = "account")
    @ApiModelProperty(value="用户账户")
    private String account;

    /**
     * 账号密码，加密
     */
    @TableField(value = "password")
    @ApiModelProperty(value="账号密码，加密")
    private String password;

    /**
     * 用户邮箱
     */
    @TableField(value = "email")
    @ApiModelProperty(value="用户邮箱")
    private String email;

    /**
     * 用户联系电话
     */
    @TableField(value = "phone")
    @ApiModelProperty(value="用户联系电话")
    private String phone;

    /**
     * 用户性别
     */
    @TableField(value = "sex")
    @ApiModelProperty(value="用户性别")
    private String sex;

    /**
     * 用户生日
     */
    @TableField(value = "birth")
    @ApiModelProperty(value="用户生日")
    private String birth;

    /**
     * 用户年龄
     */
    @TableField(value = "age")
    @ApiModelProperty(value="用户年龄")
    private Integer age;

    /**
     * 用户职业
     */
    @TableField(value = "profession")
    @ApiModelProperty(value="用户职业")
    private String profession;

    /**
     * 用户头像url
     */
    @TableField(value = "user_head_portrait_url")
    @ApiModelProperty(value="用户头像url")
    private String userHeadPortraitUrl;

    /**
     * 用户豆瓣主页链接
     */
    @TableField(value = "user_url")
    @ApiModelProperty(value="用户豆瓣主页链接")
    private String userUrl;

    public static final String COL_USER_ID = "user_id";

    public static final String COL_USER_NAME = "user_name";

    public static final String COL_USER_UNIQUE_NAME = "user_unique_name";

    public static final String COL_ACCOUNT = "account";

    public static final String COL_PASSWORD = "password";

    public static final String COL_EMAIL = "email";

    public static final String COL_PHONE = "phone";

    public static final String COL_SEX = "sex";

    public static final String COL_BIRTH = "birth";

    public static final String COL_AGE = "age";

    public static final String COL_PROFESSION = "profession";

    public static final String COL_USER_HEAD_PORTRAIT_URL = "user_head_portrait_url";

    public static final String COL_USER_URL = "user_url";
}