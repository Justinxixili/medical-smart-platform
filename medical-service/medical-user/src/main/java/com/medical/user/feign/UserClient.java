package com.medical.user.feign;

import com.medical.feign.user.IUserClient;
import com.medical.model.user.pojos.User;
import com.medical.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @Override
    @PostMapping("/user/client")
    public void createUser(@RequestBody User newUser) {
            userService.createUser(newUser); // 返回新用户 ID
    }
    @Override
    @GetMapping("/user/client/role/check")
    public boolean hasRole(@RequestParam("id") Integer id, @RequestParam("role") String role) {
        return userService.hasRole(id, role);
    }

    @Override
    @PostMapping("/user/client/role/add")
    public void addRoleToUser(@RequestParam("id") Integer id, @RequestParam("role") String role) {
        userService.addRoleToUser(id, role);
    }
}
