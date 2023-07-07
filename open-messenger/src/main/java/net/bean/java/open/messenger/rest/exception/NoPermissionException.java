package net.bean.java.open.messenger.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static net.bean.java.open.messenger.exception.ExceptionConstants.USER_DOES_NOT_HAVE_PERMISSION;

public class NoPermissionException extends ResponseStatusException {

    public NoPermissionException() {
        super(HttpStatus.FORBIDDEN, USER_DOES_NOT_HAVE_PERMISSION);
    }
}
