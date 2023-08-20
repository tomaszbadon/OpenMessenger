package net.bean.java.open.messenger.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.text.MessageFormat;

import static net.bean.java.open.messenger.exception.ExceptionConstants.USER_ALREADY_EXIST;

public class UserAlreadyExistsException extends ResponseStatusException {

    public UserAlreadyExistsException(String username) {
        super(HttpStatus.BAD_REQUEST, MessageFormat.format(USER_ALREADY_EXIST, username));
    }
}
