package com.pcy.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

/**
 * 登录成功的结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "登录的结果")
public class LoginResult {

    /**
     * 是否登录成功标志
     */
    @ApiModelProperty(value = "登录成功的标志")
    private Boolean isLoginSuccess;

    /**
     * 登录成功的token，来自authorization-server
     */
    @ApiModelProperty(value = "登录成功的token，来自authorization-server")
    private String token;

    /**
     * 其他数据，自由定制
     */

    /**
     * 该用户的菜单数据
     */
    @ApiModelProperty(value = "该用户的菜单数据")
    private List<String> menus;

    /**
     * 该用户的权限数据
     */
    @ApiModelProperty(value = "该用户的权限数据")
    private List<SimpleGrantedAuthority> authorities;

}
