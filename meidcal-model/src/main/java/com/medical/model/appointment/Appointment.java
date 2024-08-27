package com.medical.model.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-07-09 21:28
 */
@Data
public class Appointment {
    private Integer id;
    private Integer patientId;
    private Integer doctorId;
    private Integer departmentId;
    private String status;
    private String money;
    private String code;
    private String campus;
    private String address;
    private String patientName;
    private String departmentPhone;
    private String appointmentType;
    private String doctorName;
    private String departmentName;
    @JsonFormat(pattern = "yyy年MM月dd日 HH:mm")
    private LocalDateTime appointmentDate;
    @JsonFormat(pattern = "yyy年MM月dd日 HH:mm:ss")
    private LocalDateTime createTime;//创建时间
    @JsonFormat(pattern = "yyy年MM月dd日 HH:mm:ss")
    private LocalDateTime updateTime;//更新时间
}