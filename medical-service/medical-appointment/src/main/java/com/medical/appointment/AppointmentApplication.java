package com.medical.appointment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-08-07 17:26
 */
@SpringBootApplication
@MapperScan("com.medical.appointment.mapper")
@EnableFeignClients("com.medical.feign")
public class AppointmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppointmentApplication.class, args);
        System.out.println("预约服务启动成功");
    }
}
