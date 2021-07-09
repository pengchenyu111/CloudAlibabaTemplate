package com.pcy.config;

import com.pcy.constant.KeyConstant;
import com.pcy.service.impl.UserServiceDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/**
 * 授权配置
 */
@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private static final String OUTSIDE_AUTH_NAME = "appname-outside-api";
    private static final String OUTSIDE_AUTH_SECRET = "appname-outside-secret";
    private static final String INSIDE_AUTH_NAME = "appname-inside-api";
    private static final String INSIDE_AUTH_SECRET = "appname-inside-secret";
    private static final int ACCESS_TOKEN_VALIDITY = 7 * 24 * 3600;
    private static final int REFRESH_TOKEN_VALIDITY = 30 * 24 * 3600;
    private static final int INSIDE_TOKEN_VALIDITY = Integer.MAX_VALUE;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserServiceDetailsServiceImpl userDetailsService;


    /**
     * 添加第三方的客户端
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                // 第三方客户端的名称
                .withClient(OUTSIDE_AUTH_NAME)
                // 第三方客户端的密钥
                .secret(passwordEncoder.encode(OUTSIDE_AUTH_SECRET))
                // 第三方客户端的授权范围
                .scopes("all")
                .authorizedGrantTypes("password", "refresh_token")
                // token的有效期
                .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY)
                // refresh_token的有效期
                .refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY)
                .and()
                .withClient(INSIDE_AUTH_NAME)
                .secret(passwordEncoder.encode(INSIDE_AUTH_SECRET))
                .authorizedGrantTypes("client_credentials")
                .scopes("all")
                .accessTokenValiditySeconds(INSIDE_TOKEN_VALIDITY)
        ;
        super.configure(clients);
    }

    /**
     * 配置验证管理器，userDetailsService
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenStore(jwtTokenStore())
                .tokenEnhancer(jwtAccessTokenConverter());
        super.configure(endpoints);
    }

    private TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        // 加载私钥
        ClassPathResource classPathResource = new ClassPathResource(KeyConstant.PRIVATE_KEY);
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(classPathResource, KeyConstant.STORE_PASS.toCharArray());
        tokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair(KeyConstant.KEY_ALIAS, KeyConstant.KEY_PASS.toCharArray()));
        return tokenConverter;
    }

}
