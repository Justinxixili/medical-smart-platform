package com.medical.uniapp.gateway.filter;
import com.medical.utils.common.JwtUtil;
import com.medical.utils.thread.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@Slf4j
public class AuthorizeFilter implements Ordered, GlobalFilter {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 跳过不需要验证的路径
        String path = request.getURI().getPath();
        if (path.contains("/login") ||
                path.contains("/user/code") ||
                path.contains("/user/register") ||
                path.contains("/user/forgotPassword") ||
                path.contains("/user/loginForPhone")) {
            return chain.filter(exchange);
        }

        // 获取token
        String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        // 判断token是否存在
        if (StringUtils.isEmpty(token)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        try {
            // 从Redis中获取token
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            String redisToken = operations.get(token);
            if (redisToken == null) {
                // token失效
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }

            // 验证token
            Map<String, Object> claims = JwtUtil.parseToken(token);
            if (claims == null) {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }

            // 将claims存入ThreadLocal
            ThreadLocalUtil.set(claims);

            // 将claims数据放入请求头中
            ServerHttpRequest modifiedRequest = request.mutate().headers(httpHeaders -> {
                claims.forEach((key, value) -> {
                    httpHeaders.add(key, value.toString());
                });
            }).build();

            exchange = exchange.mutate().request(modifiedRequest).build();
        } catch (Exception e) {
            log.error("Token validation failed", e);
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        // 放行
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
