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
    @Select("SELECT d.doctor_id AS doctorId, d.user_id AS userId, u.username As username, d.specialty, d.title, " +
            "d.education, d.license_number AS licenseNumber, d.years_of_practice AS yearsOfPractice, " +
            "d.hospital, dep.name AS departmentName, d.working_hours AS workingHours, d.room_number AS roomNumber, " +
            "d.certifications, d.training, d.appointment_slots AS appointmentSlots, d.max_appointments_per_day AS maxAppointmentsPerDay, " +
            "d.employment_status AS employmentStatus " +
            "FROM doctor d " +
            "LEFT JOIN user u ON d.user_id = u.id " +
            "LEFT JOIN department dep ON d.department_id = dep.id")
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
