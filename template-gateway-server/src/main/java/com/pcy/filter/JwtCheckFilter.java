package com.pcy.filter;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

/**
 * 全局JWT过滤器
 */
@Component
public class JwtCheckFilter implements GlobalFilter, Ordered {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${no.require.urls:/user/register,/user/login}")
    private Set<String> noRequireTokenUris;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1 检查该接口是否需要token才能访问
        if (!isRequireToken(exchange)) {
            // 不需要token, 直接放行
            return chain.filter(exchange);
        }
        // 2 取出用户的token
        String token = getUserToken(exchange);
        // 3 判断用户的token是否有效
        if (StringUtils.isEmpty(token)) {
            System.out.println("1111111111111111111111111111");
            return buildeNoAuthorizationResult(exchange);
        }
        Boolean hasKey = redisTemplate.hasKey(token);
        if (hasKey != null && hasKey) {
            // token有效 ,直接放行
            return chain.filter(exchange);
        }
        System.out.println("22222222222222222222222222");
        System.out.println(token);
        return buildeNoAuthorizationResult(exchange);
    }

    /**
     * 判断该接口是否需要token
     *
     * @param exchange
     * @return
     */
    private boolean isRequireToken(ServerWebExchange exchange) {
        String path = exchange.getRequest().getURI().getPath();
        if (noRequireTokenUris.contains(path)) {
            return false;
        }
        return Boolean.TRUE;
    }

    /**
     * 从请求头里面获取用户的token
     *
     * @param exchange
     * @return
     */
    private String getUserToken(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        return token == null ? null : token.replace("Bearer ", "");
    }

    /**
     * 给用户响应一个没有token的错误
     *
     * @param exchange
     * @return
     */
    private Mono<Void> buildeNoAuthorizationResult(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().set("Content-Type", "application/json");
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("error", "NoAuthorization");
        jsonObject.put("errorMsg", "Token is Null or Error");
        DataBuffer wrap = response.bufferFactory().wrap(jsonObject.toJSONString().getBytes());
        return response.writeWith(Flux.just(wrap));
    }

    /**
     * 拦截器的顺序
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
