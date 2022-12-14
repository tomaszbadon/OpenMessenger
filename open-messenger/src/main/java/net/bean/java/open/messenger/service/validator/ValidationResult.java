package net.bean.java.open.messenger.service.validator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ValidationResult<E extends RuntimeException> {

    private final E exception;

    @Getter
    private final boolean result;

    public E getException() {
        if(result) {
            throw new IllegalStateException("The result of the verification is 'positive' (An exception does not exist).");
        }
        return exception;
    }

    public static <E extends RuntimeException> ValidationResult positive() {
        return new ValidationResult(null, true);
    }

    public static <E extends RuntimeException> ValidationResult negative(E exception) {
        return new ValidationResult(exception, false);
    }
}
