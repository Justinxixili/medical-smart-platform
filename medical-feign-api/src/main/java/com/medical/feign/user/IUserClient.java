package com.medical.feign.user;

import com.medical.model.user.pojos.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-08-08 23:13
 */
@FeignClient("medical-user")
public interface IUserClient {
    @GetMapping("/user/client/{id}")
    User findUserById(@PathVariable("id") Integer id);
}
