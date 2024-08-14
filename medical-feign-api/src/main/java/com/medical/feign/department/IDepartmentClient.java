package com.medical.feign.department;

import com.medical.model.department.Department;
import com.medical.model.doctor.Doctor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-08-09 0:17
 */
@FeignClient("medical-department")
public interface IDepartmentClient {
    @GetMapping("/departments/client/{id}")
    Department findDepartmentById(@PathVariable("id") Integer id);
}