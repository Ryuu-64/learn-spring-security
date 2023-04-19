package org.ryuu.learn.spring.security.exception.handler;

import org.ryuu.learn.spring.security.exception.RequestException;
import org.ryuu.learn.spring.security.dto.exception.RequestExceptionBody;
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
