package com.medical.doctor.service.Impl;



import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.medical.doctor.mapper.DoctorMapper;
import com.medical.doctor.service.DoctorService;
import com.medical.feign.department.IDepartmentClient;
import com.medical.feign.doctor.IDoctorClient;
import com.medical.feign.user.IUserClient;
import com.medical.model.common.dtos.PageBean;
import com.medical.model.common.dtos.Result;
import com.medical.model.department.Department;
import com.medical.model.doctor.Doctor;
import com.medical.model.user.pojos.User;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-07-08 13:09
 */
@Service
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    private DoctorMapper doctorMapper;
    @Autowired
    private IUserClient iUserClient;

    @Autowired
    private IDepartmentClient iDepartmentClient;

    @Override
    public PageBean<Doctor> findAll(Integer pageNum, Integer pageSize) {

        PageBean<Doctor> doctors=new PageBean<>();
        PageHelper.startPage(pageNum,pageSize);
        List<Doctor> ds=doctorMapper.findAll();
        for(Doctor doctor:ds){
            User user=iUserClient.findUserById(doctor.getUserId());
            if(user!=null){
                doctor.setUsername(user.getUsername());
                doctor.setUserId(user.getId());
            }
            Department department=iDepartmentClient.findDepartmentById(doctor.getDepartmentId());
            if(department!=null){
                doctor.setDepartmentName(department.getName());
            }
        }
        Page<Doctor> p=(Page<Doctor>) ds;
        doctors.setTotal(p.getTotal());
        doctors.setItems(p.getResult());
        return doctors;
    }

    @Override
    public void deleteUser(Integer id) {
        doctorMapper.delete(id);
    }

    @Override
    public void update(Doctor doctor) {
        doctorMapper.update(doctor);
    }

    @Override
    public Doctor getById(Integer doctorId) {
        return doctorMapper.getById(doctorId);
    }

    @Override
    public Doctor getUserId(Integer userId) {
        return doctorMapper.getUserId(userId);
    }

    @Override
    public Result getdepartment(Integer departmentId) {
       List<Doctor> doctors= doctorMapper.getdepartment(departmentId);
       if(doctors !=null &&!doctors.isEmpty()){
           doctors.parallelStream().forEach(doctor -> {
               User user=iUserClient.findUserById(doctor.getUserId());
               doctor.setUsername(user.getUsername());
           });
           return Result.success(doctors);
       }else {
           return  Result.success("没有找到医生");
       }



    }
}
