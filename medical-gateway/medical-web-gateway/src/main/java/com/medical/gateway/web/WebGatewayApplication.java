package com.medical.gateway.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-08-06 15:45
 */
@SpringBootApplication
@EnableDiscoveryClient
public class WebGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebGatewayApplication.class,args);
    }
}
