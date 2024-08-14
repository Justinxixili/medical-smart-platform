package com.medical.department.mapper;



import com.medical.model.department.Department;
import com.medical.model.department.Offices;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author xixil
 * @version 1.0
 * @date 2024-07-09 22:29
 */

@Mapper
public interface DepartmentMapper   {

    @Insert("INSERT INTO department (name, description) VALUES (#{name}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void myinsert(Department department);

    @Select("SELECT * FROM department WHERE id = #{id}")
    Department findById(@Param("id") int id);

    @Select("SELECT * FROM department")
    List<Department> findAll();

    @Update("UPDATE department SET name = #{name}, description = #{description} WHERE id = #{id}")
    void update(Department department);

    @Delete("DELETE FROM department WHERE id = #{id}")
    void delete(@Param("id") int id);

@Select("SELECT * FROM offices")
   List<Offices> getOffices();
@Select("SELECT * FROM department WHERE office_id=#{officeId}")
    List<Department> getDepartmentOfficeId(Integer officeId);
}