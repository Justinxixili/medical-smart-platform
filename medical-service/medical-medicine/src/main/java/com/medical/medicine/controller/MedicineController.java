package com.medical.medicine.controller;

import com.medical.medicine.service.MedicineService;
import com.medical.model.common.dtos.PageBean;
import com.medical.model.common.dtos.Result;
import com.medical.model.medicine.Medicine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RefreshScope
@RestController
@RequestMapping("/medicines")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @GetMapping
    public Result<PageBean<Medicine>> getAllMedicines(Integer pageNum, Integer pageSize) {
        PageBean<Medicine> medicines =medicineService.findAll(pageNum,pageSize);
        return Result.success( medicines);
    }
}