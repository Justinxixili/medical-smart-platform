package com.medical.medicine.service.Impl;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-07-08 1:41
 */


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.medical.medicine.mapper.MedicineMapper;
import com.medical.medicine.service.MedicineService;
import com.medical.model.common.dtos.PageBean;
import com.medical.model.medicine.Medicine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineServiceImpl implements MedicineService {

    @Autowired
    private MedicineMapper medicineMapper;

    @Override
    public PageBean<Medicine> findAll(Integer pageNum, Integer pageSize) {
        PageBean<Medicine> us=new PageBean<>();
        PageHelper.startPage(pageNum,pageSize);
        List<Medicine> as=medicineMapper.findAll();
        Page<Medicine> p=(Page<Medicine>) as;
        us.setTotal(p.getTotal());
        us.setItems(p.getResult());
        return us;
    }

    @Override
    public Medicine getById(Integer id) {
        return medicineMapper.getById(id);
    }
}