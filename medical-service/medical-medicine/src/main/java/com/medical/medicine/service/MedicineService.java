package com.medical.medicine.service;

import com.medical.model.common.dtos.PageBean;
import com.medical.model.medicine.Medicine;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-07-08 1:41
 */


public interface MedicineService {
    PageBean<Medicine> findAll(Integer pageNum, Integer pageSize);

    Medicine getById(Integer id);
}
