package com.medical.doctor.mapper;


import com.medical.model.doctor.DoctorEmployment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-07-15 21:20
 */
@Mapper
public interface DoctorEmploymentMapper
{
    @Select("SELECT d.*, u.username AS doctorName, u.identity AS identity "+
            "FROM doctoremployment d "+
    "LEFT JOIN doctor do ON d.doctor_id=do.doctor_id "+
    "LEFT JOIN user u ON do.user_id=u.id")
    List<DoctorEmployment> getlist();

    @Update("update doctoremployment set employment_status=#{employmentStatus},hire_date=#{hireDate},resignation_date=#{resignationDate},update_time=#{updateTime} where id=#{id}")
    void update(DoctorEmployment doctorEmployment);
@Delete("DELETE FROM doctoremployment WHERE id=#{id}")
    void delete(Integer id);
}
