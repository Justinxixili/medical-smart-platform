package com.medical.appointment.service.Impl;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-07-09 21:29
 */

import com.medical.appointment.mapper.AppointmentMapper;
import com.medical.appointment.service.AppointmentService;
import com.medical.feign.department.IDepartmentClient;
import com.medical.feign.doctor.IDoctorClient;
import com.medical.feign.patient.IPatientClient;
import com.medical.feign.user.IUserClient;
import com.medical.model.appointment.Appointment;
import com.medical.model.common.dtos.Result;
import com.medical.model.department.Department;
import com.medical.model.doctor.Doctor;
import com.medical.model.patient.Patient;
import com.medical.model.user.pojos.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppointmentServiceImpl implements AppointmentService{

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Autowired
    private IPatientClient iPatientClient;
    @Autowired
    private IDoctorClient iDoctorClient;
    @Autowired
    private IDepartmentClient iDepartmentClient;
    @Autowired
    private IUserClient iUserClient;
    @Override
    public Result createAppointment(Appointment appointment,Integer userId) {
        // 获取当前时间
        appointment.setCreateTime(LocalDateTime.now());
        appointment.setUpdateTime(LocalDateTime.now());
        Patient patientForUser=iPatientClient.findUserId(userId);
        // 获取当前用户的ID
        appointment.setPatientId(patientForUser.getPatientId());

        // 验证患者是否存在
        Patient patient = iPatientClient.findPatientById(appointment.getPatientId());
        if (patient == null) {
            return Result.error("无效的患者ID");
        }
        // 验证科室是否存在
      Department department = iDepartmentClient.findDepartmentById(appointment.getDepartmentId());
        if (department == null) {
            return Result.error("无效的科室ID");
        }
        // 设置其他相关信息
        appointment.setDepartmentName(department.getName());
        appointment.setCampus(department.getCampus());
        appointment.setAddress(department.getAddress());
        String code = generateCode(LocalDateTime.now(), department.getId());
        appointment.setCode(code);


        appointment.setAppointmentDate(appointment.getAppointmentDate());
        // 插入预约记录
        appointmentMapper.insert(appointment);
        return Result.success("预约创建成功");
    }
    private String generateCode(LocalDateTime dateTime, Integer doctorId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String dateStr = dateTime.format(formatter);
        return dateStr + "-" + doctorId;
    }
    @Override
    public void updateAppointment(Appointment appointment) {
        appointment.setCreateTime(LocalDateTime.now());
        appointment.setUpdateTime(LocalDateTime.now());
        appointmentMapper.update(appointment);
    }

    @Override
    public Result deleteAppointment(Integer id) {
        appointmentMapper.delete(id);
        return  Result.success();
    }



    @Override
    public Result getAppointmentsByUserId(Integer userId) {
        // 获取患者信息
        Patient topatient = iPatientClient.findUserId(userId);
        if (topatient == null) {
            return Result.error("未找到该患者");
        }
        // 获取该患者的所有预约
        List<Appointment> appointments = appointmentMapper.findPatientStatus(topatient.getPatientId());
        if (appointments == null || appointments.isEmpty()) {
            return Result.success("没有找到此预约");
        }
        if (appointments != null && !appointments.isEmpty()) {
            // 并行处理每个预约的信息填充
            appointments.parallelStream().forEach(appointment -> {
                Patient patient = iPatientClient.findPatientById(appointment.getPatientId());
                Department department = iDepartmentClient.findDepartmentById(appointment.getDepartmentId());
                User patientUser = iUserClient.findUserById(patient.getUserId());
                // 设置预约详细信息
                appointment.setPatientName(patientUser.getUsername());
                appointment.setDepartmentName(department.getName());
                appointment.setCampus(department.getCampus());
                appointment.setAddress(department.getAddress());
                appointment.setDepartmentPhone(department.getDepartmentPhone());
            });
            // 准备返回的数据
            Map<String, Object> data = new HashMap<>();
            data.put("total", appointments.size());
            data.put("items", appointments);
            return Result.success(data);
        } else {
            return Result.success("没有找到此预约");
        }
    }

    @Override
    public Result getApUserId(Integer patientId) {
        List<Appointment> appointments = appointmentMapper.findMyClientPatietnId(patientId);
        if(appointments !=null && !appointments.isEmpty()){
            for(Appointment appointment :appointments){
                Patient patient=iPatientClient.findPatientById(appointment.getPatientId());
                Doctor doctor=iDoctorClient.findDoctorById(appointment.getDoctorId());
                Department department=iDepartmentClient.findDepartmentById(doctor.getDepartmentId());
                User  patientUser=iUserClient.findUserById(patient.getUserId());
               User doctorUser=iUserClient.findUserById(doctor.getUserId());
                appointment.setPatientName(patientUser.getUsername());
                appointment.setDoctorName(doctorUser.getUsername());
                appointment.setDepartmentName(department.getName());
                appointment.setCampus(department.getCampus());
                appointment.setAddress(department.getAddress());
            }
            Map<String ,Object> data=new HashMap<>();
            data.put("total",appointments.size());
            data.put("items",appointments);
            return Result.success(data);
        }else {
            return Result.success("没有找到此预约");
        }

    }

    @Override
    public Result getAppointmentUsername(String username,String identity) {
        User user=iUserClient.findUserByUsername(username,identity);
        // 获取患者信息
        Patient topatient = iPatientClient.findUserId(user.getId());
        if (topatient == null) {
            return Result.error("未找到该患者");
        }
        // 获取该患者的所有预约
        List<Appointment> appointments = appointmentMapper.findMyClientPatietnId(topatient.getPatientId());
        if (appointments == null || appointments.isEmpty()) {
            return Result.success("没有找到此预约");
        }
        if (appointments != null && !appointments.isEmpty()) {
            // 并行处理每个预约的信息填充
            appointments.parallelStream().forEach(appointment -> {
                Patient patient = iPatientClient.findPatientById(appointment.getPatientId());
                Department department = iDepartmentClient.findDepartmentById(appointment.getDepartmentId());
                User patientUser = iUserClient.findUserById(patient.getUserId());
                // 设置预约详细信息
                appointment.setPatientName(patientUser.getUsername());
                appointment.setDepartmentName(department.getName());
                appointment.setCampus(department.getCampus());
                appointment.setAddress(department.getAddress());
                appointment.setDepartmentPhone(department.getDepartmentPhone());
            });
            // 准备返回的数据

            return Result.success(appointments);
        } else {
            return Result.success("没有找到此预约");
        }
    }
}