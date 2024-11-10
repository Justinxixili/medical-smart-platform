package com.medical.department;

import com.medical.department.feign.DepartmentClient;
import com.medical.feign.department.IDepartmentClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-08-09 0:05
 */
@SpringBootApplication
@MapperScan("com.medical.department.mapper")
@EnableFeignClients(clients = {IDepartmentClient.class})
public class DepartmentApplication {
    public static void main(String[] args) {
        SpringApplication.run(DepartmentApplication.class, args);
        System.out.println("部门服务启动成功");
    }
}