package com.medical.doctor.controller;


import com.medical.doctor.service.DoctorEmploymentService;
import com.medical.model.common.dtos.PageBean;
import com.medical.model.common.dtos.Result;
import com.medical.model.doctor.DoctorEmployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-07-15 21:11
 */
@RestController
@RequestMapping("/doctorEmployment")
public class DoctorEmploymentController {

    @Autowired
    private DoctorEmploymentService doctorEmploymentService;
    @GetMapping
    public Result<PageBean<DoctorEmployment>> allemployment(Integer pageNum, Integer pageSize){
        PageBean<DoctorEmployment> de=doctorEmploymentService.findlist(pageNum,pageSize);
        return Result.success(de);
    }

    @PutMapping
    public Result update(@RequestBody @Validated DoctorEmployment doctorEmployment){
        doctorEmploymentService.update(doctorEmployment);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result deleteEmployment(@PathVariable Integer id){
        doctorEmploymentService.delete(id);
        return Result.success();
    }
}
