package org.ryuu.learn.spring.security.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ryuu.learn.spring.security.dto.User;
import org.ryuu.learn.spring.security.dto.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserRoleServiceTest {
    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    private User user;

    private UserRole userRole;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("ryuu");
        user.setPassword("42");

        userRole = new UserRole();
        userRole.setUsername("ryuu");
        userRole.setRole("ROLE_ADMIN");
    }

    @Test
    void create() {
        userRoleService.deleteByRole(userRole);
        int rowsAffected = userRoleService.create(userRole);
        assertEquals(1, rowsAffected);
    }

    @Test
    void queryByUsername() {
        userRoleService.create(userRole);
        List<UserRole> authorities = userRoleService.queryByUsername(userRole.getUsername());
        UserRole resUserRole = authorities
                .stream()
                .filter(authority -> authority.getUsername().equals(userRole.getUsername()))
                .findFirst()
                .orElse(null);
        assertEquals(userRole, resUserRole);
    }

    @Test
    void queryByRole() {
        userRoleService.create(userRole);
        List<UserRole> authorities = userRoleService.queryByRole(userRole.getRole());
        UserRole resUserRole = authorities
                .stream()
                .filter(currAuthority -> currAuthority.getRole().equals(userRole.getRole()))
                .findFirst()
                .orElse(null);
        assertEquals(userRole, resUserRole);
    }

    @Test
    void update() {
        String username = "foo";
        userService.deleteByUsername(username);
        User user = new User();
        user.setUsername(username);
        user.setPassword("114514");
        authenticationService.register(user);
        UserRole userRole = new UserRole();
        userRole.setUsername(username);
        userRole.setRole("ROLE_MEMBER");
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                userRole.getUsername(), null,
                Collections.singleton(() -> "SET_ROLE_MEMBER")
        ));
        int rowsAffected = userRoleService.update(userRole);
        assertEquals(1, rowsAffected);
    }

    @Test
    void deleteByRole() {
        userRoleService.create(userRole);
        int rowsAffected = userRoleService.deleteByRole(userRole);
        assertEquals(1, rowsAffected);
    }

    @Test
    void deleteByUsername() {
        userService.deleteByUsername(user.getUsername());
        userRoleService.create(userRole);

        UserRole userRole2 = new UserRole();
        userRole2.setUsername(userRole.getUsername());
        userRole2.setRole("ROLE_MEMBER");
        userRoleService.create(userRole2);

        int rowsAffected = userRoleService.deleteByUsername(userRole.getUsername());
        assertEquals(2, rowsAffected);
    }
}