package com.medical.feign.doctor;

import com.medical.model.doctor.Doctor;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-08-08 23:50
 */
@FeignClient("medical-doctor")
public interface IDoctorClient {
    @GetMapping("/doctor/client/{doctorId}")
    Doctor findDoctorById(@PathVariable("doctorId") Integer doctorId);

    @GetMapping("/doctor/client/userId/{userId}")
    Doctor findUserId(@PathVariable("userId") Integer userId);
}