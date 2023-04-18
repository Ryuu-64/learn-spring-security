package org.ryuu.learn.springsecurity.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ryuu.learn.springsecurity.dto.UserRole;
import org.ryuu.learn.springsecurity.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("ryuu");
        user.setPassword("42");
    }

    @Test
    void create() {
        userService.deleteByUsername(user.getUsername());
        int rowsAffected = userService.create(user);
        assertEquals(1, rowsAffected);
    }

    @Test
    void queryAll() {
        userService.create(user);
        List<User> users = userService.queryAll();
        assertTrue(users.size() >= 1);
    }

    @Test
    void queryByUsername() {
        userService.create(user);
        User user = userService.queryByUsername(this.user.getUsername());
        assertEquals(this.user.getUsername(), user.getUsername());
    }

    @Test
    void deleteUserWithAuthorities() {
        userService.create(user);

        UserRole userRole1 = new UserRole();
        userRole1.setUsername(user.getUsername());
        userRole1.setRole("admin");
        userRoleService.create(userRole1);

        UserRole userRole2 = new UserRole();
        userRole2.setUsername(user.getUsername());
        userRole2.setRole("member");
        userRoleService.create(userRole2);

        int rowsAffected = userService.deleteByUsername(user.getUsername());
        assertEquals(1, rowsAffected);

        List<UserRole> authorities = userRoleService.queryByUsername(user.getUsername());
        assertEquals(0, authorities.size());
    }
}