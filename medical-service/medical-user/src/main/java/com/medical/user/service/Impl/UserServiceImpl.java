package com.medical.user.service.Impl;


import cn.hutool.core.util.RandomUtil;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.medical.model.common.dtos.PageBean;
import com.medical.model.common.dtos.Result;
import com.medical.model.user.dtos.LoginRequest;
import com.medical.model.user.dtos.RegisterRequest;
import com.medical.model.user.pojos.User;
import com.medical.user.mapper.UserMapper;
import com.medical.user.service.UserService;
import com.medical.utils.common.JwtUtil;
import com.medical.utils.common.Md5Util;
import com.medical.utils.common.NumberUtil;
import com.medical.utils.thread.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import  javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.medical.utils.common.RedisConstants.*;


/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-07-03 22:14
 */
@Slf4j
@Service
public class UserServiceImpl  implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 根据手机号查找用户信息
     * @return 用户信息封装在Result对象中
     */
    @Override
    public Result findByPhone(String phone) {
        User user = userMapper.findByPhone(phone);
        return Result.success(user);
    }

    /**
     * 用户注册
     * @param registerRequest 包含注册信息的请求对象
     * @return 注册结果封装在Result对象中
     */
    @Override
    public Result register(RegisterRequest registerRequest) {
        String phone = registerRequest.getPhone();
        String password = registerRequest.getPassword();
        String identity = registerRequest.getIdentity();
        String username = registerRequest.getUsername();
        String role = registerRequest.getRole();
        // 加密密码
        String md5String = Md5Util.getMD5String(password);

        User u = userMapper.findByPhone(phone);
        User i = userMapper.findByIdentity(identity);
        if (u == null && i == null) {
            userMapper.add(phone, md5String, role, username, identity);
            return Result.success();
        } else {
            return Result.error("身份证或者手机号已被占用");
        }
    }

    /**
     * 删除用户
     * @param id 用户ID
     * @return 删除结果封装在Result对象中
     */
    @Override
    public Result deleteUser(Integer id) {
        userMapper.delete(id);
        return Result.success();
    }

    /**
     * 根据身份证查找用户信息
     * @param identity 身份证号
     * @return 用户信息
     */
    @Override
    public User findByIdentity(String identity) {
        return userMapper.findByIdentity(identity);
    }

    /**
     * 发送手机验证码
     * @param phone 用户的手机号
     * @return 发送结果封装在Result对象中
     */
    @Override
    public Result sendCode(String phone) {
        // 校验手机号格式
        if (!NumberUtil.isPhone(phone)) {
            return Result.error("手机号格式错误！");
        }
        // 生成验证码
        String code = RandomUtil.randomNumbers(6);
        // 保存在Redis中
        stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY + phone, code, LOGIN_CODE_TTL, TimeUnit.MINUTES);

        log.debug("发送验证码成功，验证码：{}", code);
        return Result.success(code);
    }

    /**
     * 用户名和密码登录
     * @param loginRequest 包含登录信息的请求对象
     * @return 登录结果封装在Result对象中
     */
    @Override
    public Result loginForPassword(LoginRequest loginRequest) {
        String phone = loginRequest.getPhone();
        String password = loginRequest.getPassword();

        User u = userMapper.findByPhone(phone);
        if (u == null) {
            return Result.error("没有找到此用户");
        }

        if (Md5Util.getMD5String(password).equals(u.getPassword())) {
            HashMap<String, Object> claims = new HashMap<>();
            claims.put("id", u.getId());
            claims.put("phone", u.getPhone());
            claims.put("role", u.getRole());
            String token = JwtUtil.genToken(claims);
            // 把token存储到Redis中
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token, token, 1, TimeUnit.HOURS);


            return Result.success(token);
        }
        return Result.error("密码错误");
    }

    /**
     * 手机验证码登录
     * @param loginRequest 包含登录信息的请求对象
     * @return 登录结果封装在Result对象中
     */
    @Override
    public Result loginForPhone(LoginRequest loginRequest) {
        String phone = loginRequest.getPhone();
        String code = loginRequest.getCode();

        // 校验手机号格式
        if (!NumberUtil.isPhone(phone)) {
            return Result.error(ERROR_PHONE_FORMAT);
        }

        // 校验验证码，从Redis中获取
        String cacheCode = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY + phone);
        if (cacheCode == null || !cacheCode.equals(code)) {
            return Result.error(ERROR_VERIFICATION_CODE);
        }

        // 根据手机号查询用户
        User user = userMapper.findByPhone(phone);
        if (user == null) {
            user = createUserWithPhone(phone);
        }

        // 生成token
        String token = generateToken(user);
        // 将token存储到Redis
        storeTokenInRedis(token, user);

        // 构建响应

        return Result.success(token);
    }

    /**
     * 生成token
     * @param user 用户对象
     * @return 生成的token
     */
    private String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("phone", user.getPhone());
        claims.put("role", user.getRole());
        claims.put("username", user.getUsername());
        return JwtUtil.genToken(claims);
    }

    /**
     * 将token存储到Redis中
     * @param token 生成的token
     * @param user 用户对象
     */
    private void storeTokenInRedis(String token, User user) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", user.getId());
        userMap.put("phone", user.getPhone());
        userMap.put("role", user.getRole());
        userMap.put("username", user.getUsername());

        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.set(token, JwtUtil.genToken(userMap), TOKEN_EXPIRATION, TimeUnit.HOURS);
    }

    /**
     * 创建带手机号的用户
     * @param phone 用户手机号
     * @return 创建的用户对象
     */
    public User createUserWithPhone(String phone) {
        User user = new User();
        user.setPhone(phone);
        user.setUsername(USER_NICK_NAME_PREFIX + RandomUtil.randomString(10));
        user.setPassword(RandomUtil.randomString(8));  // 设置随机密码
        user.setIdentity("defaultIdentity"); // 设置默认身份证号
        user.setRole("define"); // 设置默认角色
        userMapper.add(user.getPhone(), user.getPassword(), user.getUsername(), user.getIdentity(), user.getRole());
        return user;
    }

    /**
     * 忘记密码
     * @param forgotPasswordRequest 包含忘记密码信息的请求对象
     * @return 忘记密码操作结果封装在Result对象中
     */
    @Override
    public Result forgotPassword(LoginRequest forgotPasswordRequest) {
        String phone = forgotPasswordRequest.getPhone();
        String code = forgotPasswordRequest.getCode();
        String newPassword = forgotPasswordRequest.getPassword();

        // 校验手机号格式
        if (!NumberUtil.isPhone(phone)) {
            return Result.error("手机号格式错误！");
        }

        // 校验验证码，从Redis中获取
        String cacheCode = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY + phone);
        if (cacheCode == null || !cacheCode.equals(code)) {
            return Result.error("验证码错误！");
        }

        // 根据手机号查询用户
        User user = userMapper.findByPhone(phone);
        if (user == null) {
            return Result.error("用户不存在！");
        }

        // 更新用户密码
        userMapper.updatePwd(Md5Util.getMD5String(newPassword), user.getId());

        return Result.success("密码重置成功！");
    }

    @Override
    public User getById(Integer id) {
        return userMapper.getById(id);
    }

    @Override
    public User findByUsername(String username,String identity) {
        return userMapper.findByUsername(username,identity);
    }

    /**
     * 更新用户信息
     * @param user 包含更新信息的用户对象
     * @return 更新结果封装在Result对象中
     */
    @Override
    public Result update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
        return Result.success();
    }

    /**
     * 更新用户头像
     * @param avatarUrl 新的头像URL
     * @return 更新结果封装在Result对象中
     */
    @Override
    public Result updateAvatar(String avatarUrl) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updateAvatar(avatarUrl, id);
        return Result.success();
    }

    /**
     * 更新用户密码
     * @param newPwd 新密码
     */
    @Override
    public void updatePwd(String newPwd) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updatePwd(Md5Util.getMD5String(newPwd), id);
    }

/**
 * 分页获取所有用户
 * @param pageNum 页码
 * @param pageSize 每页显示的用户数量
 * @return 分页的用户信息封装在PageBean对象中
 *
 *  */
    @Override
    public PageBean<User> getAllUsers(Integer pageNum, Integer pageSize) {
        PageBean<User> us=new PageBean<>();
        PageHelper.startPage(pageNum,pageSize);
        List<User> as=userMapper.getAllUsers();
        Page<User> p=(Page<User>) as;
        us.setTotal(p.getTotal());
        us.setItems(p.getResult());
        return us;
    }


}