package org.ryuu.learn.springsecurity.exception;

import org.ryuu.learn.springsecurity.model.exception.RequestExceptionBody;
import lombok.Getter;

public class RequestException extends RuntimeException {
    @Getter
    private final RequestExceptionBody requestExceptionBody;

    public RequestException(RequestExceptionBody requestExceptionBody, Throwable cause) {
        super(requestExceptionBody.getMessage(), cause);
        this.requestExceptionBody = requestExceptionBody;
    }
}
