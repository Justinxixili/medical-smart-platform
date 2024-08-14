package com.medical.doctor.service.Impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.medical.doctor.mapper.DoctorEmploymentMapper;
import com.medical.doctor.service.DoctorEmploymentService;
import com.medical.feign.user.IUserClient;
import com.medical.model.common.dtos.PageBean;
import com.medical.model.doctor.DoctorEmployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-07-15 21:18
 */
@Service
public class DoctorEmploymentServiceImpl implements DoctorEmploymentService {
    @Autowired
    private DoctorEmploymentMapper doctorEmploymentMapper;

    @Override
    public PageBean<DoctorEmployment> findlist(Integer pageNum, Integer pageSize) {

        PageBean<DoctorEmployment> us=new PageBean<>();
        PageHelper.startPage(pageNum,pageSize);
        List<DoctorEmployment> as=doctorEmploymentMapper.getlist();
        Page<DoctorEmployment> p=(Page<DoctorEmployment>) as;
        us.setTotal(p.getTotal());
        us.setItems(p.getResult());
        return us;
    }

    @Override
    public void update(DoctorEmployment doctorEmployment) {
        doctorEmployment.setUpdateTime(LocalDateTime.now());
        doctorEmploymentMapper.update(doctorEmployment);
    }

    @Override
    public void delete(Integer id) {
        doctorEmploymentMapper.delete(id);
    }
}
