package org.ryuu.learn.springsecurity.exception;

import lombok.Setter;
import lombok.ToString;
import org.ryuu.learn.springsecurity.dto.exception.RequestExceptionBody;
import lombok.Getter;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
public class RequestException extends RuntimeException {
    private RequestExceptionBody requestExceptionBody;

    private HttpStatus httpStatus;

    public RequestException(Logger logger, String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.requestExceptionBody = new RequestExceptionBody(message);
        logger.warn(message, this);
    }

    public RequestException(Logger logger, HttpStatus httpStatus, String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
        this.requestExceptionBody = new RequestExceptionBody(message);
        logger.warn(message, this);
    }
}
