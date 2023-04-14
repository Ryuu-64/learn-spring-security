package org.ryuu.learn.springsecurity.service.impl;

import org.junit.jupiter.api.Test;
import org.ryuu.learn.springsecurity.dto.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthorityServiceTest {
    @Autowired
    private AuthorityService authorityService;

    private final String username = "ryuu";

    private final String authority = "admin";

    @Test
    void insert() {
        Authority authority = new Authority();
        authority.setUsername(username);
        authority.setAuthority(this.authority);
        authorityService.delete(authority);
        int rowsAffected = authorityService.insert(authority);
        assertEquals(1, rowsAffected);
    }

    @Test
    void queryByUsername() {
        Authority authority = new Authority();
        authority.setUsername(username);
        authority.setAuthority(this.authority);
        authorityService.insert(authority);
        List<Authority> authorities = authorityService.queryByUsername(username);
        Authority resAuthority = authorities
                .stream()
                .filter(currAuthority -> currAuthority.getUsername().equals(username))
                .findFirst()
                .orElse(null);
        assertEquals(authority, resAuthority);
    }

    @Test
    void queryByAuthority() {
        Authority authority = new Authority();
        authority.setUsername(username);
        authority.setAuthority(this.authority);
        authorityService.insert(authority);
        List<Authority> authorities = authorityService.queryByAuthority(this.authority);
        Authority resAuthority = authorities
                .stream()
                .filter(currAuthority -> currAuthority.getAuthority().equals(this.authority))
                .findFirst()
                .orElse(null);
        assertEquals(authority, resAuthority);
    }

    @Test
    void delete() {
        Authority authority = new Authority();
        authority.setUsername(username);
        authority.setAuthority("admin");
        authorityService.insert(authority);
        int rowsAffected = authorityService.delete(authority);
        assertEquals(1, rowsAffected);
    }
}