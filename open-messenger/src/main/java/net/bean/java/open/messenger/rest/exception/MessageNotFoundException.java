package net.bean.java.open.messenger.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.text.MessageFormat;

import static net.bean.java.open.messenger.exception.ExceptionConstants.MESSAGE_NOT_FOUND;

public class MessageNotFoundException extends ResponseStatusException {

    public MessageNotFoundException(String messageId) {
        super(HttpStatus.NOT_FOUND, MessageFormat.format(MESSAGE_NOT_FOUND, messageId));
    }

}
