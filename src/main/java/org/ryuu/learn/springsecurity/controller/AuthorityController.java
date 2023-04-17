package org.ryuu.learn.springsecurity.controller;

import lombok.AllArgsConstructor;
import org.ryuu.learn.springsecurity.dto.Authority;
import org.ryuu.learn.springsecurity.service.impl.AuthorityService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("authority")
public class AuthorityController {
    private final AuthorityService authorityService;

    @PostMapping("/delete")
    public int delete(@RequestBody Authority authority) {
        return authorityService.deleteByAuthority(authority);
    }
}
