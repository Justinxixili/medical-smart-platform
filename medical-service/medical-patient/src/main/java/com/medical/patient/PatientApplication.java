package com.medical.patient;

import com.medical.feign.patient.IPatientClient;
import com.medical.patient.feign.PatientClient;
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
@MapperScan("com.medical.patient.mapper")
@EnableFeignClients("com.medical.feign")
public class PatientApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientApplication.class, args);
        System.out.println("患者服务启动成功");
    }

}