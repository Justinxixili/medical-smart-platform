package com.medical.user.service;



import com.medical.model.common.dtos.PageBean;
import com.medical.model.common.dtos.Result;
import com.medical.model.user.dtos.LoginRequest;
import com.medical.model.user.dtos.LoginResponse;
import com.medical.model.user.dtos.RegisterRequest;
import com.medical.model.user.pojos.User;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-07-03 22:14
 */

public interface UserService  {
  Result findByPhone(String phone);

    Result register(RegisterRequest registerRequest);

   Result update(User user);

    Result updateAvatar(String avatarUrl);

    void updatePwd(String newPwd);

    PageBean<User> getAllUsers(Integer pageNum, Integer pageSize);


    Result deleteUser(Integer id);

    User findByIdentity(String identity);

    Result loginForPhone(LoginRequest loginRequest);

    Result sendCode(String phone);

    Result<LoginResponse> loginForPassword(LoginRequest loginRequest);

    Result forgotPassword(LoginRequest forgotPasswordRequest);

 User getById(Integer id);

    User findByUsername(String username,String identity);

   void createUser(User newUser);

    boolean hasRole(Integer id, String role);

    void addRoleToUser(Integer id, String role);
}