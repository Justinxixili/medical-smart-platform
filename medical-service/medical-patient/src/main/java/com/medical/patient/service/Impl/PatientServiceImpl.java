package com.medical.patient.service.Impl;



import com.alibaba.cloud.commons.lang.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.medical.feign.medicine.IMedicineClient;
import com.medical.feign.user.IUserClient;
import com.medical.model.appointment.Appointment;
import com.medical.model.common.dtos.PageBean;
import com.medical.model.common.dtos.Result;
import com.medical.model.medicine.Medicine;
import com.medical.model.patient.Patient;
import com.medical.model.user.pojos.User;
import com.medical.patient.mapper.PatientMapper;
import com.medical.patient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-07-08 14:35
 */


@Service
public class PatientServiceImpl  implements PatientService {

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private IUserClient iUserClient;

    @Autowired
    private IMedicineClient iMedicineClient;

    @Override
    public Result findById(int patientId) {
        Patient patient = patientMapper.getById(patientId);
        return  Result.success(patient);
    }

    @Override
    public Result findAll(Integer pageNum, Integer pageSize) {
        PageBean<Patient> us = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);
        List<Patient> patients = patientMapper.findAll();
        // 遍历所有的患者，利用 Feign Client 获取用户信息和药品信息

        for (Patient patient : patients) {
            // 通过 UserClient 获取用户信息
            User user = iUserClient.findUserById(patient.getUserId());
            if (user != null) {
                patient.setUsername(user.getUsername());
                patient.setPhone(user.getPhone());
            }
            // 通过 MedicineClient 获取药品信息
            Medicine medicine = iMedicineClient.findMedicineById(patient.getCurrentMedications());
            if (medicine != null) {
                patient.setCurrentMedicationName(medicine.getName());
            }
            if (StringUtils.isEmpty(patient.getFamilyMedicalHistory())) {
                patient.setFamilyMedicalHistory("无家族病史");
            }
            if (StringUtils.isEmpty(patient.getAllergies())) {
                patient.setAllergies("无过敏史");
            }
        }
        Page<Patient> p = (Page<Patient>) patients;
        us.setTotal(p.getTotal());
        us.setItems(p.getResult());

        return Result.success(us);
    }

    @Override
    public Result insert(Patient patient) {
        patientMapper.myinsert(patient);
        return Result.success("Patient created successfully");
    }

    @Override
    public Result update(Patient patient,Integer id) {
        patientMapper.update(patient);
        patient.setPatientId(id);
        return Result.success("Patient updated successfully");
    }

    @Override
    public Result delete(int patientId) {
        patientMapper.delete(patientId);
        return Result.success("Patient deleted successfully");

    }

    @Override
    public Patient getById(Integer patientId) {
        return patientMapper.getById(patientId);
    }

    @Override
    public Patient findUserId(Integer userId) {
        return patientMapper.findUserId(userId);
    }
}