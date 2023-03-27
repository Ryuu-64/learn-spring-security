package com.example.demo.exception.handler;

import com.example.demo.exception.RequestException;
import com.example.demo.model.exception.RequestExceptionBody;
import lombok.AllArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@AllArgsConstructor
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestExceptionHandler {
    @ExceptionHandler(RequestException.class)
    public ResponseEntity<RequestExceptionBody> handleRequestException(RequestException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        RequestExceptionBody exceptionBody = new RequestExceptionBody(
                httpStatus,
                e.getMessage(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(exceptionBody, httpStatus);
    }
}
