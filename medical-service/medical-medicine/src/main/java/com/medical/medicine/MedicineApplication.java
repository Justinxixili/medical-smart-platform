package com.medical.medicine;

import com.medical.feign.medicine.IMedicineClient;
import com.medical.feign.user.IUserClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-08-07 16:46
 */
@SpringBootApplication
@MapperScan("com.medical.medicine.mapper")
@EnableFeignClients(clients = {IMedicineClient.class})
public class MedicineApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicineApplication.class, args);
        System.out.println("药品服务启动成功");
    }

}