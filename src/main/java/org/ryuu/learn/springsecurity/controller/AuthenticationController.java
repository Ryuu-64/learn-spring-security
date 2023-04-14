package org.ryuu.learn.springsecurity.controller;

import org.ryuu.learn.springsecurity.dto.User;
import org.ryuu.learn.springsecurity.exception.RequestException;
import org.ryuu.learn.springsecurity.dto.auth.AuthenticationResponse;
import org.ryuu.learn.springsecurity.dto.exception.RequestExceptionBody;
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
@RequestMapping("authentication")
public class AuthenticationController {
    private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody User user) {
        try {
            return authenticationService.register(user);
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
    public AuthenticationResponse authenticate(@RequestBody User user) {
        return authenticationService.authenticate(user);
    }
}
