package org.ryuu.learn.springsecurity.service.impl;

import org.ryuu.learn.springsecurity.mapper.UserMapper;
import org.ryuu.learn.springsecurity.model.User;
import org.ryuu.learn.springsecurity.model.auth.AuthenticationRequest;
import org.ryuu.learn.springsecurity.model.auth.AuthenticationResponse;
import org.ryuu.learn.springsecurity.model.auth.RegisterRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        User user = User
                .builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userMapper.insert(user);
        return new AuthenticationResponse(jwtService.getToken(user.getUsername()));
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        String username = request.getUsername();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        request.getPassword()
                )
        );
        User requestUser = User
                .builder()
                .username(username)
                .build();
        User user = userMapper.selectOne(requestUser);
        return new AuthenticationResponse(jwtService.getToken(user.getUsername()));
    }
}
