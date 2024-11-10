package com.medical.feign.patient;

import com.medical.model.patient.pojos.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-08-08 23:40
 */
@FeignClient("medical-patient")
public interface IPatientClient {
    @GetMapping("/patient/client/{patientId}")
    Patient findPatientById(@PathVariable("patientId") Integer patientId);


    @GetMapping("/patient/client/userId/{userId}")
    Patient findUserId(@PathVariable("userId") Integer userId);
}
