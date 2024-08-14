package com.medical.model.user.pojos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

import  jakarta.validation.constraints.Email;
import  jakarta.validation.constraints.NotEmpty;
import  jakarta.validation.constraints.NotNull;
import  jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-08-05 22:52
 */
@Data
public class User {
    @NotNull
    private Integer id;//主键ID
    private String username;//用户名
    @JsonIgnore
    private String password;//密码
    @Pattern(regexp = "^(男|女)?$")
    private String gender; // 性别

    private LocalDate birth; // 出生日期

    @Pattern(regexp = "^[0-9]{10,20}$")
    private String phone; // 电话

    private String address; // 地址
    private String identity;

    @NotEmpty
    @Email
    private String email;//邮箱
    private String userPic;//用户头像地址
    @JsonFormat(pattern = "yyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间
    @JsonFormat(pattern = "yyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;//更新时间
    @NotEmpty
    @Pattern(regexp = "^(doctor|patient|admin|default)$")
    private String role;
}
