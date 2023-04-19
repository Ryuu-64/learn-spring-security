package org.ryuu.learn.spring.security.service.impl;

import lombok.AllArgsConstructor;
import org.ryuu.learn.spring.security.util.AuthenticationUtils;
import org.ryuu.learn.spring.security.dto.UserRole;
import org.ryuu.learn.spring.security.exception.RequestException;
import org.ryuu.learn.spring.security.mapper.UserRoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UserRoleService {
    private static final Logger logger = LoggerFactory.getLogger(UserRoleService.class);

    private final UserRoleMapper userRoleMapper;

    @Transactional
    public int create(UserRole userRole) {
        try {
            return userRoleMapper.insert(userRole);
        } catch (DuplicateKeyException e) {
            return -1;
        }
    }

    public List<UserRole> queryByUsername(String username) {
        return userRoleMapper.selectByUsername(username);
    }

    public List<UserRole> queryByRole(String role) {
        return userRoleMapper.selectByRole(role);
    }

    public int update(UserRole userRole) throws RequestException {
        if (!AuthenticationUtils.getAuthorities().contains("SET_" + userRole.getRole())) {
            AccessDeniedException accessDeniedException = new AccessDeniedException(
                    SecurityContextHolder.getContext().getAuthentication().toString()
            );
            throw new RequestException(
                    logger, HttpStatus.FORBIDDEN,
                    "Insufficient permissions to perform authorization operation.",
                    accessDeniedException
            );
        }

        return userRoleMapper.update(userRole);
    }

    public int deleteByRole(UserRole userRole) {
        return userRoleMapper.deleteByRole(userRole);
    }

    @Transactional
    public int deleteByUsername(String username) {
        return userRoleMapper.deleteByUsername(username);
    }
}
