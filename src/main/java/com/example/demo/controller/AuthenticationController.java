package com.example.demo.controller;

import com.example.demo.exception.RequestException;
import com.example.demo.model.auth.AuthenticationRequest;
import com.example.demo.model.auth.AuthenticationResponse;
import com.example.demo.model.auth.RegisterRequest;
import com.example.demo.service.impl.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("auth")
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody RegisterRequest request) {
        try {
            return service.register(request);
        } catch (Exception e) {
            throw new RequestException("register failed", e);
        }
    }

    @PostMapping("/authenticate")
    public AuthenticationResponse register(@RequestBody AuthenticationRequest request) {
        return service.authenticate(request);
    }
}
