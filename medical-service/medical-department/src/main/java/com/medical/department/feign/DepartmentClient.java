package com.medical.department.feign;

import com.medical.department.service.DepartmentService;
import com.medical.feign.department.IDepartmentClient;
import com.medical.model.department.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-08-09 0:19
 */
@RestController
public class DepartmentClient implements IDepartmentClient {
    @Autowired
    private DepartmentService departmentService;

    @Override
    @GetMapping("/departments/client/{id}")
    public Department findDepartmentById(@PathVariable("id") Integer id) {
        return departmentService.getById(id);
    }
}
