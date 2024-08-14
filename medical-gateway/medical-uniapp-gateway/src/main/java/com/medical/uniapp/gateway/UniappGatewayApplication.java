package com.medical.uniapp.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-08-09 11:31
 */
@SpringBootApplication
@EnableDiscoveryClient
public class UniappGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(UniappGatewayApplication.class,args);
    }
}