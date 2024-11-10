package com.medical.model.patient.pojos;

import lombok.Data;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-07-08 14:32
 */
@Data
public class Patient {
    private Integer patientId;
    private Integer userId;
    private String medicalCardNumber;
    private Integer currentMedications;
    private String username;
    private String phone;
    private String medicalHistory;
    private String currentCondition;
    private String allergies;
    private String currentMedicationName;
    private String familyMedicalHistory;
    private String bloodType;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String emergencyContactRelationship;

}