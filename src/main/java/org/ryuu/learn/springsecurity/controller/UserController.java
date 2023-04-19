package org.ryuu.learn.springsecurity.controller;

import lombok.AllArgsConstructor;
import org.ryuu.learn.springsecurity.dto.User;
import org.ryuu.learn.springsecurity.service.impl.AuthenticationService;
import org.ryuu.learn.springsecurity.service.impl.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return authenticationService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return authenticationService.login(user);
    }

    @PostMapping("/queryAll")
    public List<User> queryAll() {
        return userService.queryAll();
    }

    @PostMapping("/deleteByUsername")
    public int deleteByUsername(@RequestBody String username) {
        return userService.deleteByUsername(username);
    }
}