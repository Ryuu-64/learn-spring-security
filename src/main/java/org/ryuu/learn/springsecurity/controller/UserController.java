package org.ryuu.learn.springsecurity.controller;

import lombok.AllArgsConstructor;
import org.ryuu.learn.springsecurity.dto.User;
import org.ryuu.learn.springsecurity.service.impl.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    @PostMapping("/queryAll")
    public List<User> queryAll() {
        return userService.queryAll();
    }

    @PostMapping("/deleteUserWithAuthorities")
    public int deleteUserWithAuthorities(@RequestBody String username) {
        return userService.deleteUserWithAuthorities(username);
    }
}