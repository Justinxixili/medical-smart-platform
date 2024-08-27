package com.medical.user.controller;

import com.medical.model.common.dtos.PageBean;
import com.medical.model.common.dtos.Result;
import com.medical.model.user.dtos.LoginRequest;
import com.medical.model.user.dtos.LoginResponse;
import com.medical.model.user.dtos.RegisterRequest;
import com.medical.model.user.pojos.Menu;
import com.medical.model.user.pojos.User;
import com.medical.user.service.MenuService;
import com.medical.user.service.UserService;
import com.medical.utils.thread.ThreadLocalUtil;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-08-05 23:04
 */
@RefreshScope
@RestController
@Validated
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;
    /**
     * 用户注册
     * @param registerRequest 注册请求对象，包含手机号、密码、身份、用户名和角色
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result register(@RequestBody RegisterRequest registerRequest) {
        return userService.register(registerRequest);
    }

    /**
     * 发送手机验证码
     * @param phone 用户手机号
     * @return 发送验证码的结果
     */
    @PostMapping("/code")
    public Result sendCode(@RequestParam("phone") String phone) {
        return userService.sendCode(phone);
    }

    /**
     * 手机验证码登录
     * @param loginRequest 登录请求对象，包含手机号和验证码
     * @return 登录结果，包含生成的Token等信息
     */
    @PostMapping("/loginForPhone")
    public Result loginForPhone(@RequestBody LoginRequest loginRequest) {
        return userService.loginForPhone(loginRequest);
    }

    /**
     * 用户名和密码登录
     * @param loginRequest 登录请求对象，包含手机号和密码
     * @return 登录结果，包含生成的Token等信息
     */
    @PostMapping("/login")
    public Result<LoginResponse> loginForPassword(@RequestBody LoginRequest loginRequest) {
        return userService.loginForPassword(loginRequest);
    }

    /**
     * 忘记密码
     * @param forgotPasswordRequest 忘记密码请求对象，包含手机号、验证码和新密码
     * @return 重置密码的结果
     */
    @PostMapping("/forgotPassword")
    public Result forgotPassword(@RequestBody LoginRequest forgotPasswordRequest) {
        return userService.forgotPassword(forgotPasswordRequest);
    }

    /**
     * 获取当前用户信息
     * @return 当前用户的信息
     */
    @GetMapping("/userInfo")
    public Result<User> userInfo(@RequestHeader("id") String userId, @RequestHeader("phone") String phone) {
        return userService.findByPhone(phone);
    }

    /**
     * 删除用户
     * @param id 用户ID
     * @return 删除操作的结果
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteUser(@PathVariable Integer id) {
        return userService.deleteUser(id);
    }

    /**
     * 更新用户信息
     * @param user 用户对象，包含更新后的信息
     * @return 更新操作的结果
     */
    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user) {
        return userService.update(user);
    }

    /**
     * 更新用户头像
     * @param avatarUrl 新的头像URL
     * @return 更新操作的结果
     */
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        return userService.updateAvatar(avatarUrl);
    }

    /**
     * 更新用户密码
     * @param params 包含旧密码和新密码的键值对
     * @param token 用户的认证Token
     * @return 更新密码操作的结果
     */
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params, @RequestHeader("Authorization") String token) {
        return Result.success();
    }

    /**
     * 获取所有用户，分页显示
     * @param pageNum 页码
     * @param pageSize 每页显示的用户数量
     * @return 分页的用户信息
     */
    @GetMapping("/allUsers")
    public Result<PageBean<User>> getAllUsers(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageBean<User> users = userService.getAllUsers(pageNum, pageSize);
        return Result.success(users);
    }
    /**
     * 获取所有菜单项
     * @return 包含菜单项的结果
     */
    @GetMapping("/menus")
    public Result<List<Menu>> getAllMenus() {

        return menuService.getAllMenus();
    }

    /**
     * 保存一个新菜单项
     * @param menu 菜单项对象
     * @return 保存后的菜单项
     */
    @PostMapping("/menus")
    public Result<Menu> createMenu(@RequestBody Menu menu) {

        return menuService.saveMenu(menu);
    }

    /**
     * 删除一个菜单项
     * @param id 菜单项ID
     * @return 删除结果
     */
    @DeleteMapping("/menus/{id}")
    public Result<Void> deleteMenu(@PathVariable Integer id) {

        return menuService.deleteMenu(id);
    }
}
