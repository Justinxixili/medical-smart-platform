package com.medical.model.doctor;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-07-15 21:09
 */
@Data
public class DoctorEmployment {
    private Integer id;
    private Integer doctorId;
    private String DoctorName;
    private String identity;
    private String employmentStatus;
    @JsonFormat(pattern = "yyy-MM-dd HH:mm:ss")
    private Date hireDate;
    @JsonFormat(pattern = "yyy-MM-dd HH:mm:ss")
    private Date resignationDate;
    @JsonFormat(pattern = "yyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间
    @JsonFormat(pattern = "yyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;//更新时间

}
