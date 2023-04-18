package org.ryuu.learn.springsecurity.controller;

import lombok.AllArgsConstructor;
import org.ryuu.learn.springsecurity.dto.UserRole;
import org.ryuu.learn.springsecurity.service.impl.UserRoleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("role")
public class UserRoleController {
    private final UserRoleService userRoleService;

    @PostMapping("/update")
    public int update(@RequestBody UserRole userRole) {
        return userRoleService.update(userRole);
    }

    @PostMapping("/deleteByRole")
    public int deleteByRole(@RequestBody UserRole userRole) {
        return userRoleService.deleteByRole(userRole);
    }
}
