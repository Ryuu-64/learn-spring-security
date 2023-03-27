package com.example.demo.model.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
public class RequestExceptionBody {
    private final HttpStatus httpStatus;
    private final String message;
    private final ZonedDateTime dateTime;
}