package com.example.demo.service.impl;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.auth.AuthenticationRequest;
import com.example.demo.model.auth.AuthenticationResponse;
import com.example.demo.model.auth.RegisterRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.example.demo.model.User;
import org.springframework.security.core.userdetails.UserDetails;
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
        return AuthenticationResponse
                .builder()
                .token(jwtService.generateToken(user))
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        User requestUser = User.builder().build();
        UserDetails userDetails = userMapper.selectOne(requestUser);
        return AuthenticationResponse
                .builder()
                .token(jwtService.generateToken(userDetails))
                .build();
    }
}
