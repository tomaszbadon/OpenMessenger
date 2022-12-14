package net.bean.java.open.messenger.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidTokenException extends ResponseStatusException {

    public InvalidTokenException(HttpStatus status, Throwable cause) {
        super(status, ExceptionConstants.INVALID_TOKEN, cause);
    }

    public InvalidTokenException() {
        super(HttpStatus.FORBIDDEN, ExceptionConstants.INVALID_TOKEN);
    }

    public static InvalidTokenException of(HttpStatus httpStatus, Throwable throwable) {
        return new InvalidTokenException(httpStatus, throwable);
    }

    public static InvalidTokenException of() {
        return new InvalidTokenException();
    }

}
