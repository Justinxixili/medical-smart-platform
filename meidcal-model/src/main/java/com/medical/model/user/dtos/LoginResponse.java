package com.medical.model.user.dtos;

import lombok.Data;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-07-28 15:34
 */
@Data
public class LoginResponse {
    private String token;
    private Integer userId;
    private  String username;
    private String role;
    private String phone;
    public LoginResponse(String token, Integer userId,String role, String username,String phone) {
        this.token = token;
        this.userId = userId;
        this.role=role;
        this.username=username;
        this.phone=phone;
    }
}
