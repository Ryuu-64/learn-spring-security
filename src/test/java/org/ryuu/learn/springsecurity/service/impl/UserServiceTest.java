package org.ryuu.learn.springsecurity.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ryuu.learn.springsecurity.dto.Authority;
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
    private AuthorityService authorityService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("ryuu");
        user.setPassword("42");
    }

    @Test
    void create() {
        userService.deleteUserWithAuthorities(user.getUsername());
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

        Authority authority1 = new Authority();
        authority1.setUsername(user.getUsername());
        authority1.setAuthority("admin");
        authorityService.create(authority1);

        Authority authority2 = new Authority();
        authority2.setUsername(user.getUsername());
        authority2.setAuthority("member");
        authorityService.create(authority2);

        int rowsAffected = userService.deleteUserWithAuthorities(user.getUsername());
        assertEquals(1, rowsAffected);

        List<Authority> authorities = authorityService.queryByUsername(user.getUsername());
        assertEquals(0, authorities.size());
    }
}