package com.pcy.service;

import com.pcy.model.LoginResult;

/**
 * @author PengChenyu
 * @since 2021-07-01 20:54:51
 */
public interface GeneralUserLoginService {

    /**
     * 登录的实现
     *
     * @param username 用户名
     * @param password 用户的密码
     * @return 登录的结果
     */
    LoginResult login(String username, String password);


}
