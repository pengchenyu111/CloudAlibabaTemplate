package com.pcy.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author PengChenyu
 * @since 2021-06-30 16:23:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieUser implements Serializable {
    private static final long serialVersionUID = -5355473222550189803L;
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户唯一名字标志，短评上没有id，以此做唯一标识
     */
    private String userUniqueName;

    /**
     * 用户账户
     */
    private String account;

    /**
     * 账号密码，加密
     */
    private String password;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户联系电话
     */
    private String phone;

    /**
     * 用户性别
     */
    private String sex;

    /**
     * 用户生日
     */
    private String birth;

    /**
     * 用户年龄
     */
    private Integer age;

    /**
     * 用户职业
     */
    private String profession;

    /**
     * 用户头像url
     */
    private String userHeadPortraitUrl;

    /**
     * 用户豆瓣主页链接
     */
    private String userUrl;
}