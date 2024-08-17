package com.medical.doctor.mapper;



import com.medical.model.doctor.Doctor;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-07-08 13:08
 */
@Mapper
public interface DoctorMapper  {
 @Select("SELECT * FROM doctor")
    List<Doctor> findAll();

    @Delete("DELETE FROM doctor WHERE doctor_id = #{id}")
    void delete(Integer id);

    @Update("update doctor set  employment_status=#{employmentStatus} where doctor_id=#{doctorId}")
    void update(Doctor doctor);
    @Select("SELECT * FROM doctor WHERE doctor_id=#{doctorId}")
    Doctor getById(@Param("doctorId")Integer doctorId);
    @Select("SELECT * FROM doctor WHERE user_id=#{userId}")
    Doctor getUserId(Integer userId);

    @Select("SELECT * FROM doctor WHERE department_id=#{departmentId}")
    List<Doctor> getdepartment(Integer departmentId);
}
