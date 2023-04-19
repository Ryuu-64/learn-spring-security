package org.ryuu.learn.springsecurity.dto.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
public class RequestExceptionBody {
    private final String message;

    private final ZonedDateTime dateTime;

    public RequestExceptionBody(String message) {
        this.message = message;
        dateTime = ZonedDateTime.now(ZoneId.of("Z"));
    }
}
