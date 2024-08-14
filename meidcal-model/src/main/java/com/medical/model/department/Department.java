package com.medical.model.department;

import lombok.Data;

/**
 * @author xixil
 * @version 1.0
 * @date 2024-07-09 22:28
 */
@Data
public class Department {
    private int id;
    private Integer officeId;
    private String name;
    private String description;
    private String campus;
    private String address;
}