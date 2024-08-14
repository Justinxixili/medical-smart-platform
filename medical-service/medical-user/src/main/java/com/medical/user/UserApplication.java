package com.medical.user;


import com.medical.feign.user.IUserClient;
import com.medical.user.feign.UserClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-08-05 22:49
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.medical.user.mapper")
@EnableFeignClients(clients = {IUserClient.class})
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }
}
