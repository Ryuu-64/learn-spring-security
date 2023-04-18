package org.ryuu.learn.springsecurity.service.impl;

import org.ryuu.learn.springsecurity.dto.User;
import org.ryuu.learn.springsecurity.dto.UserRole;
import org.ryuu.learn.springsecurity.dto.exception.RequestExceptionBody;
import org.ryuu.learn.springsecurity.exception.RequestException;
import org.ryuu.learn.springsecurity.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserMapper userMapper;

    private final UserRoleService userRoleService;

    private final String defaultRole;

    public UserService(
            UserMapper userMapper, UserRoleService userRoleService,
            @Value("${spring.security.default-role}") String defaultRole
    ) {
        this.userMapper = userMapper;
        this.userRoleService = userRoleService;
        this.defaultRole = defaultRole;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = queryByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("username not found");
        }
        return user;
    }

    @Transactional
    public int create(User user) {
        int rowsAffected = userMapper.insert(user);
        UserRole userRole = new UserRole();
        userRole.setUsername(user.getUsername());
        userRole.setRole(defaultRole);
        try {
            userRoleService.create(userRole);
        } catch (DuplicateKeyException e) {
            String message = "create user role failed";
            logger.warn(message, e);
            throw new RequestException(
                    new RequestExceptionBody(HttpStatus.BAD_REQUEST, message), e
            );
        }
        return rowsAffected;
    }

    public List<User> queryAll() {
        return userMapper.selectAll();
    }

    public User queryByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Transactional
    public int deleteByUsername(String username) {
        int rowsAffected = userMapper.deleteByUsername(username);
        userRoleService.deleteByUsername(username);
        return rowsAffected;
    }
}
