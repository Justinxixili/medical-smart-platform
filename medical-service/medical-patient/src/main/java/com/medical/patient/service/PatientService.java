package com.medical.patient.service;



import com.medical.model.common.dtos.Result;
import com.medical.model.patient.patientDTO.patientCardDTO;
import com.medical.model.patient.pojos.Patient;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-07-08 14:35
 */


public interface PatientService  {
    Result findById(int patientId);
    Result findAll(Integer pageNum, Integer pageSize);
    Result insert(Patient patient);
    Result update(Patient patient,Integer id);
    Result delete(int patientId);



    Patient getById(Integer patientId);

    Patient findUserId(Integer userId);

    Result<String> addPatientCard(patientCardDTO patientCardDTO);

    Result<patientCardDTO> getPatientCard(Integer userId);
}