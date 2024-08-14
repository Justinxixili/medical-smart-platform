package com.medical.patient.mapper;



import com.medical.model.patient.Patient;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-07-08 14:33
 */


@Mapper
public interface PatientMapper {

    @Select("SELECT * FROM patient ")
    List<Patient> findAll();

    @Insert("INSERT INTO patient(user_id, username, medical_history, current_condition, phone, allergies, current_medications, current_medication_name, " +
            "family_medical_history, blood_type, emergency_contact_name, emergency_contact_phone, emergency_contact_relationship, " +
            "appointment_date, appointment_time, appointment_department, appointment_doctor) " +
            "VALUES(#{userId}, #{username}, #{medicalHistory}, #{currentCondition}, #{phone}, #{allergies}, #{currentMedications}, #{currentMedicationName}, " +
            "#{familyMedicalHistory}, #{bloodType}, #{emergencyContactName}, #{emergencyContactPhone}, #{emergencyContactRelationship}, " +
            "#{appointmentDate}, #{appointmentTime}, #{appointmentDepartment}, #{appointmentDoctor})")
    @Options(useGeneratedKeys = true, keyProperty = "patientId")
    void myinsert(Patient patient);

    @Update("UPDATE patient SET user_id=#{userId}, username=#{username}, medical_history=#{medicalHistory}, current_condition=#{currentCondition}, " +
            "phone=#{phone}, allergies=#{allergies}, current_medications=#{currentMedications}, current_medication_name=#{currentMedicationName}, " +
            "family_medical_history=#{familyMedicalHistory}, blood_type=#{bloodType}, emergency_contact_name=#{emergencyContactName}, " +
            "emergency_contact_phone=#{emergencyContactPhone}, emergency_contact_relationship=#{emergencyContactRelationship}, " +
            "appointment_date=#{appointmentDate}, appointment_time=#{appointmentTime}, appointment_department=#{appointmentDepartment}, " +
            "appointment_doctor=#{appointmentDoctor} WHERE patient_id = #{patientId}")
    void update(Patient patient);

    @Delete("DELETE FROM patient WHERE patient_id = #{patientId}")
    void delete(int patientId);
    @Select("SELECT * FROM patient WHERE patient_id=#{patientId}")
    Patient getById(@Param("patientId")Integer patientId);
    @Select("SELECT * FROM patient WHERE user_id=#{userId}")
    Patient findUserId(Integer userId);

}
