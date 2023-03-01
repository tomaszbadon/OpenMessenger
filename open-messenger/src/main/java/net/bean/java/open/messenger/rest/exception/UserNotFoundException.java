package net.bean.java.open.messenger.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static net.bean.java.open.messenger.rest.exception.ExceptionConstants.RECIPIENT_DOES_NOT_EXIST_IN_REPOSITORY;

import java.text.MessageFormat;

public class UserNotFoundException extends ResponseStatusException {

    public UserNotFoundException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }

    public UserNotFoundException(long userId) {
        super(HttpStatus.BAD_REQUEST, MessageFormat.format(RECIPIENT_DOES_NOT_EXIST_IN_REPOSITORY, userId));
    }

}
