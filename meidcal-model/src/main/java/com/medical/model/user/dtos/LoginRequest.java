package com.medical.model.user.dtos;

import lombok.Data;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-07-25 11:52
 */
@Data
public class LoginRequest {
    private String phone;
    private String password;
    private String code;
}
