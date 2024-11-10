package com.medical.patient.feign;

import com.medical.feign.patient.IPatientClient;

import com.medical.model.patient.pojos.Patient;

import com.medical.patient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-08-08 23:37
 */

@RestController
public class PatientClient implements IPatientClient {
    @Autowired
    private PatientService patientService;

    @Override
    @GetMapping("/patient/client/{patientId}")
    public Patient findPatientById(@PathVariable("patientId") Integer patientId) {
        return patientService.getById(patientId);
    }

    @Override
    @GetMapping("/patient/client/userId/{userId}")
    public Patient findUserId(@PathVariable("userId") Integer userId){
        return patientService.findUserId(userId);
    }

}
