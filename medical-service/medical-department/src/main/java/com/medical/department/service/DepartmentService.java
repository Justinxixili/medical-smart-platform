package com.medical.department.service;




import com.medical.model.common.dtos.Result;
import com.medical.model.department.Department;

import java.util.List;

/**
 * @author xixil
 * @version 1.0
 * @date 2024-07-09 22:29
 */

public interface DepartmentService   {
    void addDepartment(Department department);
    Result getDepartmentById(int id);
    List<Department> getAllDepartments();
    void updateDepartment(Department department);
    void deleteDepartment(int id);

    Department getById(Integer id);

    Result getOffices();

    Result getDepartmentOfficeId(Integer officeId);
}