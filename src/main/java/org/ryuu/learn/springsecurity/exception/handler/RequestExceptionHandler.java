package org.ryuu.learn.springsecurity.exception.handler;

import org.ryuu.learn.springsecurity.exception.RequestException;
import org.ryuu.learn.springsecurity.dto.exception.RequestExceptionBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RequestExceptionHandler {
    @ExceptionHandler(RequestException.class)
    public ResponseEntity<RequestExceptionBody> handleRequestException(RequestException requestException) {
        RequestExceptionBody body = requestException.getRequestExceptionBody();
        return new ResponseEntity<>(body, requestException.getHttpStatus());
    }
}
