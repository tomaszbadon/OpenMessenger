package net.bean.java.open.messenger.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.text.MessageFormat;

import static net.bean.java.open.messenger.rest.exception.ExceptionConstants.USER_DOES_NOT_EXIST_IN_REPOSITORY;

public class UserNotFoundException extends ResponseStatusException {

    public UserNotFoundException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }

    public static UserNotFoundException withUserId(String userId) {
        return new UserNotFoundException(MessageFormat.format(USER_DOES_NOT_EXIST_IN_REPOSITORY, userId));
    }

}
