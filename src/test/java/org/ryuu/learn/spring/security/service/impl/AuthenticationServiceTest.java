package org.ryuu.learn.spring.security.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ryuu.learn.spring.security.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthenticationServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("ryuu");
        user.setPassword("42");
    }

    @Test
    void register() {
        userService.deleteByUsername(user.getUsername());
        assertNotNull(authenticationService.register(user));
    }

    @Test
    void login() {
        userService.deleteByUsername(user.getUsername());
        authenticationService.register(user);
        user.setPassword("42");
        authenticationService.login(user);
    }
}