package org.ryuu.learn.spring.security.service.impl;

import org.ryuu.learn.spring.security.dto.User;
import org.ryuu.learn.spring.security.dto.UserRole;
import org.ryuu.learn.spring.security.exception.RequestException;
import org.ryuu.learn.spring.security.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final JwtService jwtService;

    private final UserRoleService userRoleService;

    private final UserMapper userMapper;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final String defaultRole;

    public AuthenticationService(
            JwtService jwtService,
            UserRoleService userRoleService,
            UserMapper userMapper,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            @Value("${spring.security.default-role}") String defaultRole
    ) {
        this.jwtService = jwtService;
        this.userRoleService = userRoleService;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.defaultRole = defaultRole;
    }

    @Transactional
    public String register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            userMapper.insert(user);
        } catch (DuplicateKeyException duplicateKeyException) {
            throw new RequestException(logger, "register failed, user already exists", duplicateKeyException);
        }
        UserRole userRole = new UserRole();
        userRole.setUsername(user.getUsername());
        userRole.setRole(defaultRole);
        try {
            userRoleService.create(userRole);
        } catch (DuplicateKeyException duplicateKeyException) {
            throw new RequestException(logger, "create user role failed", duplicateKeyException);
        }
        return jwtService.getToken(user.getUsername());
    }

    public String login(User user) {
        String username = user.getUsername();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    username,
                    user.getPassword()
            ));
        } catch (BadCredentialsException badCredentialsException) {
            throw new RequestException(
                    logger, HttpStatus.UNAUTHORIZED, "invalid username or password", badCredentialsException
            );
        }
        return jwtService.getToken(username);
    }
}
