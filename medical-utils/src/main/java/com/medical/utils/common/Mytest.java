package com.medical.utils.common;

import org.junit.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
@SpringBootTest
public class Mytest {


    //jwt测试
    @Test
    public void testTokenGenerationAndParsing() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 51);
        claims.put("phone", "1234567890");
        claims.put("role", "patient");

        String token = JwtUtil.genToken(claims);
        System.out.println("Generated Token: " + token);

        Map<String, Object> parsedClaims = JwtUtil.parseToken(token);
        System.out.println("Parsed Claims: " + parsedClaims);
    }



}
