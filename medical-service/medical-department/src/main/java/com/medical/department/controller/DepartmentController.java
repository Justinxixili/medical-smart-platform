package com.medical.department.controller;


import com.medical.department.service.DepartmentService;
import com.medical.model.common.dtos.Result;
import com.medical.model.department.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xixil
 * @version 1.0
 * @date 2024-07-09 22:28
 */
@RefreshScope
@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/add")
    public Result<String> addDepartment(@RequestBody Department department) {
        departmentService.addDepartment(department);
        return Result.success("Department added successfully!");
    }

    @GetMapping("/id")
    public Result<Department> getDepartmentById(@RequestParam("id") Integer id) {

        return departmentService.getDepartmentById(id);
    }

    @GetMapping
    public Result<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        return Result.success(departments);
    }

    @PutMapping("/update")
    public Result<String> updateDepartment(@RequestBody Department department) {
        departmentService.updateDepartment(department);
        return Result.success("Department updated successfully!");
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> deleteDepartment(@PathVariable int id) {
        departmentService.deleteDepartment(id);
        return Result.success("Department deleted successfully!");
    }

    @GetMapping("/offices")
    public Result getOffices(){
        return departmentService.getOffices();
    }

    @GetMapping("/getoffices")
    public Result getDepartmentOfficesId(@RequestParam("officeId") Integer officeId){
        return departmentService.getDepartmentOfficeId(officeId);
    }
}