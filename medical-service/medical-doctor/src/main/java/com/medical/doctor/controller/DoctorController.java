package com.medical.doctor.controller;


import com.medical.doctor.service.DoctorService;
import com.medical.model.common.dtos.PageBean;
import com.medical.model.common.dtos.Result;
import com.medical.model.doctor.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-07-08 13:08
 */
@RefreshScope
@RestController
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;
    @GetMapping
    public Result<PageBean<Doctor>> getAllDoctors(Integer pageNum, Integer pageSize) {
        PageBean<Doctor> doctors=doctorService.findAll(pageNum, pageSize);

        return Result.success(doctors);
    }
    @DeleteMapping("/{id}")
    public Result deleteUser(@PathVariable Integer id) {
        doctorService.deleteUser(id);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated Doctor doctor) {
        doctorService.update(doctor);
        return Result.success();
    }

    @GetMapping("/getdepartment")
    public Result getdepartment(@RequestParam("departmentId") Integer departmentId){
        return doctorService.getdepartment(departmentId);
    }
}
