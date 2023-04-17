package org.ryuu.learn.springsecurity.service.impl;

import lombok.AllArgsConstructor;
import org.ryuu.learn.springsecurity.dto.User;
import org.ryuu.learn.springsecurity.dto.auth.AuthenticationResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.create(user);
        String token = jwtService.getToken(user.getUsername());
        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse authenticate(User user) {
        String username = user.getUsername();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        user.getPassword()
                )
        );
        String token = jwtService.getToken(username);
        return new AuthenticationResponse(token);
    }
}
