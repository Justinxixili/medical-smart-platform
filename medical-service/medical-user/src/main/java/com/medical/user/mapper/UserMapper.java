package com.medical.user.mapper;



import com.medical.model.user.pojos.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-07-03 22:14
 */
@Mapper
public interface UserMapper{

    /**
     * 添加新用户
     * @param phone 用户手机号
     * @param password 加密后的密码
     * @param username 用户名
     * @param identity 用户身份
     */
    @Insert("INSERT INTO user(phone, password, username, identity, create_time, update_time) VALUES(#{phone}, #{password}, #{username}, #{identity}, NOW(), NOW())")
    void add(String phone, String password,  String username, String identity);

    @Insert("INSERT INTO user(phone, password, username, identity, role, create_time, update_time) VALUES(#{phone}, #{password}, #{username}, #{identity},#{role}, NOW(), NOW())")
    void createUser(String phone, String password, String username, String identity,String role);

    /**
     * 根据手机号查询用户
     * @param phone 用户手机号
     * @return 用户对象
     */
    @Select("SELECT * FROM user WHERE phone = #{phone}")
    User findByPhone(@Param("phone") String phone);

    /**
     * 更新用户信息
     * @param user 用户对象
     */
    @Update("UPDATE user SET gender = #{gender}, birth = #{birth}, address = #{address}, user_pic = #{userPic}, email = #{email}, role = #{role}, update_time = NOW() WHERE id = #{id}")
    void update(User user);

    /**
     * 根据用户ID删除用户
     * @param id 用户ID
     */
    @Delete("DELETE FROM user WHERE id = #{id}")
    void delete(Integer id);

    /**
     * 更新用户头像
     * @param avatarUrl 用户头像URL
     * @param id 用户ID
     */
    @Update("UPDATE user SET user_pic = #{avatarUrl}, update_time = NOW() WHERE id = #{id}")
    void updateAvatar(@Param("avatarUrl") String avatarUrl, @Param("id") Integer id);

    /**
     * 更新用户密码
     * @param md5String 加密后的新密码
     * @param id 用户ID
     */
    @Update("UPDATE user SET password = #{md5String}, update_time = NOW() WHERE id = #{id}")
    void updatePwd(@Param("md5String") String md5String, @Param("id") Integer id);

    /**
     * 获取所有用户
     * @return 用户列表
     */
    @Select("SELECT * FROM user")
    List<User> getAllUsers();


    /**
     * 根据身份查询用户
     * @param identity 用户身份
     * @return 用户对象
     */
    @Select("SELECT * FROM user WHERE identity = #{identity}")
    User findByIdentity(@Param("identity") String identity);
    @Select("SELECT * FROM user WHERE id = #{id}")
    User getById(@Param("id")Integer id);
    @Select("SELECT * FROM user WHERE username = #{username} AND identity = #{identity}")
    User findByUsername(String username,String identity);



    // 使用 @Select 注解查询用户是否具有指定角色
    @Select("SELECT 1 FROM user WHERE id = #{id} AND role = #{role} LIMIT 1")
    Integer countUserRole(@Param("id") Integer id, @Param("role") String role);

    @Update("UPDATE user SET role = #{role} WHERE id = #{id}")
    void updateUserRole(@Param("id") Integer id, @Param("role") String role);
}
