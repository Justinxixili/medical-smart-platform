package com.medical.department.service.Impl;



import com.medical.department.mapper.DepartmentMapper;
import com.medical.department.service.DepartmentService;
import com.medical.model.common.dtos.Result;
import com.medical.model.department.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xixil
 * @version 1.0
 * @date 2024-07-09 22:28
 */
@Service
public class DepartmentServiceImpl   implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public void addDepartment(Department department) {
        departmentMapper.myinsert(department);
    }

    @Override
    public Result getDepartmentById(int id) {
        Department department = departmentMapper.findById(id);
        if (department != null) {
            return Result.success(department);
        } else {
            return Result.error("Department not found!");
        }
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentMapper.findAll();
    }

    @Override
    public void updateDepartment(Department department) {
        departmentMapper.update(department);
    }

    @Override
    public void deleteDepartment(int id) {
        departmentMapper.delete(id);
    }

    @Override
    public Department getById(Integer id) {
        return departmentMapper.findById(id);
    }

    @Override
    public Result getOffices() {
        return Result.success(departmentMapper.getOffices());

    }

    @Override
    public Result getDepartmentOfficeId(Integer officeId) {
        return  Result.success(departmentMapper.getDepartmentOfficeId(officeId));
    }
}
