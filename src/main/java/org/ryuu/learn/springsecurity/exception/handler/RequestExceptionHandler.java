package org.ryuu.learn.springsecurity.exception.handler;

import org.ryuu.learn.springsecurity.exception.RequestException;
import org.ryuu.learn.springsecurity.model.exception.RequestExceptionBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RequestExceptionHandler {
    @ExceptionHandler(RequestException.class)
    public ResponseEntity<RequestExceptionBody> handleRequestException(RequestException e) {
        RequestExceptionBody body = e.getRequestExceptionBody();
        return new ResponseEntity<>(body, body.getHttpStatus());
    }
}
