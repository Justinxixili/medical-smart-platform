package com.medical.doctor.feign;

import com.medical.doctor.service.DoctorService;
import com.medical.feign.doctor.IDoctorClient;
import com.medical.model.doctor.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-08-08 23:53
 */
@RestController
public class DoctorClient implements IDoctorClient {

    @Autowired
    private DoctorService doctorService;


    @Override
    @GetMapping("/doctor/client/{doctorId}")
    public Doctor findDoctorById(@PathVariable("doctorId")Integer doctorId) {
        return doctorService.getById(doctorId);
    }

    @Override
    @GetMapping("/doctor/client/userId/{userId}")
    public Doctor findUserId(@PathVariable("userId")Integer userId){
        return doctorService.getUserId(userId);
    }
}
