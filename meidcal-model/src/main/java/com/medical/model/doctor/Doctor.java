package com.medical.model.doctor;

import lombok.Data;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-07-08 13:02
 */
@Data
public class Doctor {
    private Integer doctorId;
    private Integer userId;
    private String username;
    private String specialty;
    private String title;
    private String education;
    private String licenseNumber;
    private Integer yearsOfPractice;
    private String hospital;
    private Integer departmentId;
    private String departmentName;
    private String workingHours;
    private String roomNumber;
    private String certifications;
    private String training;
    private String appointmentSlots;
    private Integer maxAppointmentsPerDay;
    private String employmentStatus;

}
