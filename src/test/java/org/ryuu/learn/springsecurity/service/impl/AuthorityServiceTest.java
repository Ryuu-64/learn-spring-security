package org.ryuu.learn.springsecurity.service.impl;

import org.junit.jupiter.api.Test;
import org.ryuu.learn.springsecurity.dto.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AuthorityServiceTest {
    @Autowired
    private AuthorityService authorityService;

    private final String username = "ryuu";

    private final String authority = "admin";

    @Test
    void create() {
        Authority authority = new Authority();
        authority.setUsername(username);
        authority.setAuthority(this.authority);
        authorityService.deleteByAuthority(authority);
        int rowsAffected = authorityService.create(authority);
        assertEquals(1, rowsAffected);
    }

    @Test
    void queryByUsername() {
        Authority authority = new Authority();
        authority.setUsername(username);
        authority.setAuthority(this.authority);
        authorityService.create(authority);
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
        authorityService.create(authority);
        List<Authority> authorities = authorityService.queryByAuthority(this.authority);
        Authority resAuthority = authorities
                .stream()
                .filter(currAuthority -> currAuthority.getAuthority().equals(this.authority))
                .findFirst()
                .orElse(null);
        assertEquals(authority, resAuthority);
    }

    @Test
    void deleteByAuthority() {
        Authority authority = new Authority();
        authority.setUsername(username);
        authority.setAuthority(this.authority);
        authorityService.create(authority);
        int rowsAffected = authorityService.deleteByAuthority(authority);
        assertEquals(1, rowsAffected);
    }

    @Test
    void deleteByUsername() {
        Authority authority1 = new Authority();
        authority1.setUsername(username);
        authority1.setAuthority("admin");
        authorityService.create(authority1);

        Authority authority2 = new Authority();
        authority2.setUsername(username);
        authority2.setAuthority("member");
        authorityService.create(authority2);

        int rowsAffected = authorityService.deleteByUsername(username);
        assertEquals(2, rowsAffected);
    }
}