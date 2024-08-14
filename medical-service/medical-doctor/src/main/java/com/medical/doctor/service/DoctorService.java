package com.medical.doctor.service;



import com.medical.model.common.dtos.PageBean;
import com.medical.model.common.dtos.Result;
import com.medical.model.doctor.Doctor;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-07-08 13:09
 */
public interface DoctorService  {
    PageBean<Doctor> findAll(Integer pageNum, Integer pageSize);

    void deleteUser(Integer id);

    void update(Doctor doctor);

    Doctor getById(Integer doctorId);

    Doctor getUserId(Integer userId);

    Result getdepartment(Integer departmentId);
}
