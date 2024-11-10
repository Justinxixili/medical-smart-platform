package com.medical.doctor;

import com.medical.doctor.feign.DoctorClient;
import com.medical.feign.doctor.IDoctorClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-08-07 16:45
 */
@SpringBootApplication
@MapperScan("com.medical.doctor.mapper")
@EnableFeignClients("com.medical.feign")
public class DoctorApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoctorApplication.class, args);
        System.out.println("医生服务启动成功");
    }

}