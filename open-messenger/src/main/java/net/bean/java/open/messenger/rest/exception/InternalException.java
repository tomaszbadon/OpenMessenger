package net.bean.java.open.messenger.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.text.MessageFormat;

import static net.bean.java.open.messenger.rest.exception.ExceptionConstants.INTERNAL_EXCEPTION;

public class InternalException extends ResponseStatusException {

    public InternalException(Exception exception) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, MessageFormat.format(INTERNAL_EXCEPTION, exception.getMessage()));
    }

}
