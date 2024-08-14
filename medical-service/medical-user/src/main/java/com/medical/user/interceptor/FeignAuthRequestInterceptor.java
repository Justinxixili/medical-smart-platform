package com.medical.user.interceptor;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-08-09 3:17
 */

import com.medical.utils.thread.ThreadLocalUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FeignAuthRequestInterceptor implements RequestInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void apply(RequestTemplate template) {
        // 获取当前请求的 Token
        String token = getTokenFromHeader();

        if (token != null) {
            // 从 Redis 中验证 Token 是否存在（即是否有效）
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            String redisToken = operations.get(token);

            if (redisToken != null) {
                // 如果 Token 有效，则将其附加到 Feign 请求的 Authorization 头中
                template.header("Authorization", token);
            }
        }
    }

    /**
     * 获取当前请求中的 Token，这里假设 Token 保存在 ThreadLocal 中。
     * 你可以根据实际情况调整获取 Token 的方法。
     */
    private String getTokenFromHeader() {
        Map<String, Object> claims = ThreadLocalUtil.get();
        if (claims != null) {
            return (String) claims.get("token");  // 假设 Token 保存在 claims 中的 "token" 字段
        }
        return null;
    }
}