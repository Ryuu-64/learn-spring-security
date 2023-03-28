package org.ryuu.learn.springsecurity.controller;

import org.ryuu.learn.springsecurity.exception.RequestException;
import org.ryuu.learn.springsecurity.model.auth.AuthenticationRequest;
import org.ryuu.learn.springsecurity.model.auth.AuthenticationResponse;
import org.ryuu.learn.springsecurity.model.auth.RegisterRequest;
import org.ryuu.learn.springsecurity.model.exception.RequestExceptionBody;
import org.ryuu.learn.springsecurity.service.impl.AuthenticationService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("auth")
public class AuthenticationController {
    private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationService service;

    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody RegisterRequest request) {
        try {
            return service.register(request);
        } catch (DuplicateKeyException e) {
            String invalidUserName = "register failed, invalid user name.";
            logger.warn(invalidUserName, e);
            throw new RequestException(new RequestExceptionBody(
                    HttpStatus.CONFLICT,
                    invalidUserName
            ), e);
        } catch (Exception e) {
            String registerFailed = "register failed";
            logger.warn(registerFailed, e);
            throw new RequestException(new RequestExceptionBody(registerFailed), e);
        }
    }

    @PostMapping("/authenticate")
    public AuthenticationResponse register(@RequestBody AuthenticationRequest request) {
        return service.authenticate(request);
    }
}
