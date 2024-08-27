package com.medical.user.feign;

import com.medical.feign.user.IUserClient;
import com.medical.model.user.pojos.User;
import com.medical.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-08-08 23:18
 */
@RestController
public class UserClient implements IUserClient {
    @Autowired
    private UserService userService;
    @Override
    @GetMapping("/user/client/{id}")
    public User findUserById(@PathVariable("id") Integer id) {
        return userService.getById(id);
    }

    @Override
   @GetMapping("/user/client/username")
    public User findUserByUsername( @RequestParam String username,
                                    @RequestParam String identity ) {
        return userService.findByUsername(username, identity);
    }
}
