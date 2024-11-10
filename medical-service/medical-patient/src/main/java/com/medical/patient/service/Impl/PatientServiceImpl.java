package com.medical.patient.service.Impl;



import com.alibaba.cloud.commons.lang.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.medical.feign.medicine.IMedicineClient;
import com.medical.feign.user.IUserClient;
import com.medical.model.common.dtos.PageBean;
import com.medical.model.common.dtos.Result;
import com.medical.model.medicine.Medicine;
import com.medical.model.patient.patientDTO.patientCardDTO;
import com.medical.model.patient.pojos.Patient;
import com.medical.model.user.pojos.User;
import com.medical.patient.mapper.PatientMapper;
import com.medical.patient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    @Override
    public Result<String> addPatientCard(patientCardDTO patientCardDTO) {
        // 医院代码
        String hospitalCode = "803";
        // 患者标识数字
        String patientIdentifier = "1";
        String EmergencyContactName = patientCardDTO.getEmergencyContactName();
        String EmergencyContactPhone = patientCardDTO.getEmergencyContactPhone();
        String EmergencyContactRelationship = patientCardDTO.getEmergencyContactRelationship();
        // 获取当前日期，格式为 "yyyyMMdd"
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // 查询用户是否存在
        User userOfPatient = iUserClient.findUserByUsername(patientCardDTO.getUsername(), patientCardDTO.getIdentity());
        Patient patient = new Patient();

        // 如果用户不存在，创建新用户
        if (userOfPatient == null) {
            // 初始密码设置为123456

            // 创建一个新用户对象
            User newUser = new User();
            newUser.setUsername(patientCardDTO.getUsername());
            newUser.setIdentity(patientCardDTO.getIdentity());
            newUser.setPhone(patientCardDTO.getPhone());
            newUser.setRole("patient");  // 设定患者角色

            // 插入用户信息，并获取用户ID
            iUserClient.createUser(newUser);
            User selectNewUserId = iUserClient.findUserByUsername(newUser.getUsername(), newUser.getIdentity());
            Integer newUserId = selectNewUserId.getId();

            // 检查是否已经绑定过就诊卡
            if (patientMapper.existsByUserId(newUserId)) {
                return Result.error("用户已绑定过就诊卡");
            }

            // 设置患者信息并插入
            patient.setUserId(newUserId);
            patient.setMedicalCardNumber(hospitalCode + patientIdentifier + currentDate + newUserId);
            patient.setEmergencyContactName(EmergencyContactName);
            patient.setEmergencyContactPhone(EmergencyContactPhone);
            patient.setEmergencyContactRelationship(EmergencyContactRelationship);

            // 插入患者信息
            try {
                patientMapper.addPatientCard(patient);
            } catch (Exception e) {
                return Result.error("用户创建成功，但绑定就诊卡失败: " + e.getMessage());
            }

            return Result.success("用户创建成功并绑定就诊卡");

        } else {
            Integer existingUserId = userOfPatient.getId();

            // 检查是否已经绑定过就诊卡
            if (patientMapper.existsByUserId(existingUserId)) {
                return Result.error("用户已绑定过就诊卡");
            }

            // 检查用户是否已经有患者角色，如果没有则添加患者角色
            if (!iUserClient.hasRole(existingUserId, "patient")) {
                iUserClient.addRoleToUser(existingUserId, "patient");
            }

            // 设置患者信息并绑定就诊卡
            patient.setUserId(existingUserId);
            patient.setMedicalCardNumber(hospitalCode + patientIdentifier + currentDate + existingUserId);
            patient.setEmergencyContactName(EmergencyContactName);
            patient.setEmergencyContactPhone(EmergencyContactPhone);
            patient.setEmergencyContactRelationship(EmergencyContactRelationship);

            // 绑定就诊卡
            try {
                patientMapper.addPatientCard(patient);
            } catch (Exception e) {
                return Result.error("绑定就诊卡失败: " + e.getMessage());
            }

            return Result.success("用户已存在，绑定就诊卡成功");
        }
    }

    @Override
    public Result<patientCardDTO> getPatientCard(Integer userId) {
        patientCardDTO patientCardDTO = new patientCardDTO();
        User user = iUserClient.findUserById(userId);
        if (user != null) {
            Patient patient = patientMapper.getPatientCard(userId);
            if (patient != null) {
                patientCardDTO.setUsername(user.getUsername());
                patientCardDTO.setEmergencyContactName(patient.getEmergencyContactName());
                patientCardDTO.setEmergencyContactPhone(patient.getEmergencyContactPhone());
                patientCardDTO.setEmergencyContactRelationship(patient.getEmergencyContactRelationship());
                patientCardDTO.setMedicalCardNumber(patient.getMedicalCardNumber());
                patientCardDTO.setPhone(user.getPhone());
                patientCardDTO.setUserId(patient.getUserId());
                patientCardDTO.setIdentity(user.getIdentity());

            }
        }
      return Result.success(patientCardDTO);
    }
}