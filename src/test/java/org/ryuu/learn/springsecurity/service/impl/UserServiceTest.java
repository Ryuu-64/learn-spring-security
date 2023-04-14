package org.ryuu.learn.springsecurity.service.impl;

import org.junit.jupiter.api.Test;
import org.ryuu.learn.springsecurity.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    private final String username = "ryuu";

    @Test
    void create() {
        User user = new User();
        user.setUsername(username);
        user.setPassword("test");
        int rowsAffected = userService.create(user);
        assertEquals(1, rowsAffected);
    }

    @Test
    void queryAll() {
        List<User> users = userService.queryAll();
        System.out.println(users);
    }

    @Test
    void queryByUsername() {
        User user = userService.queryByUsername(username);
        System.out.println(user);
    }

    @Test
    void deleteByUsername() {
        userService.deleteByUsername(username);
    }
}