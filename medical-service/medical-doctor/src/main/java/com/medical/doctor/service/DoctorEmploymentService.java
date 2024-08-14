package com.medical.doctor.service;


import com.medical.model.common.dtos.PageBean;
import com.medical.model.doctor.DoctorEmployment;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-07-15 21:18
 */
public interface DoctorEmploymentService {
    PageBean<DoctorEmployment> findlist(Integer pageNum, Integer pageSize);

    void update(DoctorEmployment doctorEmployment);

    void delete(Integer id);
}
