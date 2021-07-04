package com.pcy.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.enums.ApiErrorCode;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.pcy.feign.JwtToken;
import com.pcy.feign.OAuth2FeignClient;
import com.pcy.model.LoginResult;
import com.pcy.service.GeneralUserLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author PengChenyu
 * @since 2021-07-01 20:56:07
 */
@Service("generalUserLoginServiceImpl")
@Slf4j
public class GeneralUserLoginServiceImpl implements GeneralUserLoginService {

    @Autowired
    private OAuth2FeignClient oAuth2FeignClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${basic.token:Basic YXBwbmFtZS1vdXRzaWRlLWFwaTphcHBuYW1lLW91dHNpZGUtc2VjcmV0}")
    private String basicToken;


    /**
     * 登录的实现
     *
     * @param username 用户名
     * @param password 用户的密码
     * @return 登录的结果
     */
    @Override
    public LoginResult login(String username, String password) {
        log.info("用户{}开始登录", username);
        // 1 获取token 需要远程调用authorization-server 该服务
        ResponseEntity<JwtToken> tokenResponseEntity = oAuth2FeignClient.getToken("password", username, password, "general_user_type", basicToken);
        if (tokenResponseEntity.getStatusCode() != HttpStatus.OK) {
            throw new ApiException(ApiErrorCode.FAILED);
        }
        JwtToken jwtToken = tokenResponseEntity.getBody();
        log.info("远程调用授权服务器成功,获取的token为{}", JSON.toJSONString(jwtToken, true));
        String token = jwtToken.getAccessToken();

        // 2 查询菜单数据
        List<String> menus = new ArrayList<>();
        menus.add("menu1");
        menus.add("menu2");
        menus.add("menu3");

        // 3 查询权限数据 -> 可以从JWT中提取出来
        Jwt jwt = JwtHelper.decode(token);
        String jwtJsonStr = jwt.getClaims();
        JSONObject jwtJson = JSON.parseObject(jwtJsonStr);
        JSONArray authoritiesJsonArray = jwtJson.getJSONArray("authorities");
        List<SimpleGrantedAuthority> authorities = authoritiesJsonArray.stream()
                .map(authorityJson -> new SimpleGrantedAuthority(authorityJson.toString()))
                .collect(Collectors.toList());

        // 4 将该token 存储在redis 里面，便于网关做jwt验证
        stringRedisTemplate.opsForValue().set(token, "", jwtToken.getExpiresIn(), TimeUnit.SECONDS);
        // 5 注意返回时添加token_type
        return new LoginResult(Boolean.TRUE, jwtToken.getTokenType() + " " + token, menus, authorities);
    }
}
