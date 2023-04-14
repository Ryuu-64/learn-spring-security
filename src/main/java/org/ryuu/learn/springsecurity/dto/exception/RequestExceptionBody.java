package org.ryuu.learn.springsecurity.dto.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
public class RequestExceptionBody {
    private final HttpStatus httpStatus;

    private final String message;

    private final ZonedDateTime dateTime;

    public RequestExceptionBody(String message) {
        this.message = message;
        httpStatus = HttpStatus.BAD_REQUEST;
        dateTime = ZonedDateTime.now(ZoneId.of("Z"));
    }

    public RequestExceptionBody(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
        dateTime = ZonedDateTime.now(ZoneId.of("Z"));
    }
}
