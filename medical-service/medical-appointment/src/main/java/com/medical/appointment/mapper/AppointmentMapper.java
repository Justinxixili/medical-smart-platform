package com.medical.appointment.mapper;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-07-09 21:28
 */

import com.medical.model.appointment.Appointment;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Mapper
public interface AppointmentMapper {

    @Insert("INSERT INTO appointment (patient_id, department_id, status, create_time, update_time, appointment_date, code, money) " +
            "VALUES (#{patientId}, #{departmentId}, #{status}, #{createTime}, #{updateTime}, #{appointmentDate}, #{code}, #{money})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Appointment appointment);



    @Update("UPDATE appointment SET patient_id = #{patientId}, doctor_id = #{doctorId}, department_id = #{departmentId},create_time=#{createTime},update_time=#{updateTime}, status = #{status} WHERE id = #{id}")
    void update(Appointment appointment);

    @Delete("DELETE FROM appointment WHERE id = #{id}")
    void delete(@Param("id") Integer id);


    @Select("SELECT * FROM appointment WHERE patient_id=#{patientId}")
    List<Appointment> findMyClientPatietnId(Integer patientId);


}