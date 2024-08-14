package com.medical.patient.controller;

import com.medical.model.common.dtos.PageBean;
import com.medical.model.common.dtos.Result;
import com.medical.model.patient.Patient;
import com.medical.patient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-07-08 14:36
 */
@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/{id}")
    public Result<Patient> getPatientById(@PathVariable int id) {
        return patientService.findById(id);
    }

    @GetMapping
    public Result<PageBean<Patient>> getAllPatients(Integer pageNum, Integer pageSize) {
        return  patientService.findAll(pageNum,pageSize);
    }


    @PostMapping
    public Result<String> createPatient(@RequestBody Patient patient) {
        return patientService.insert(patient);
    }

    @PutMapping("/{id}")
    public Result<String> updatePatient(@PathVariable int id, @RequestBody Patient patient) {
        return   patientService.update( patient,id);
    }

    @DeleteMapping("/{id}")
    public Result<String> deletePatient(@PathVariable int id) {

        return   patientService.delete(id);
    }
}
