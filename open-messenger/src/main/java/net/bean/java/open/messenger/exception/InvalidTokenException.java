package net.bean.java.open.messenger.exception;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException(Throwable cause) {
        super(ExceptionConstants.INVALID_TOKEN, cause);
    }

    public InvalidTokenException() {
        super(ExceptionConstants.INVALID_TOKEN);
    }

    public static InvalidTokenException of(Throwable throwable) {
        return new InvalidTokenException(throwable);
    }

    public static InvalidTokenException of() {
        return new InvalidTokenException();
    }

}
