package com.medical.model.user.dtos;


import lombok.Data;

import  javax.validation.constraints.Pattern;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-07-27 12:07
 */
@Data
public class RegisterRequest {
    private  String token;
    @Pattern(regexp = "^\\S{5,16}$")
    private String phone;
    private String username;
    @Pattern(regexp = "^[1-9]\\d{5}(18|19|20)?\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])\\d{3}(\\d|X|x)$")
    private String identity;
    @Pattern(regexp = "^\\S{5,16}$")
    private String password;
    @Pattern(regexp = "^(doctor|patient|admin)$")
    private String role;

}
