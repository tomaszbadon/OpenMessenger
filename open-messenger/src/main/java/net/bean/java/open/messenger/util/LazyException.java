package net.bean.java.open.messenger.util;

import net.bean.java.open.messenger.rest.exception.UserNotFoundException;

import java.util.function.Supplier;

public class LazyException {

    public static Supplier<RuntimeException> lazyRuntimeException(UserNotFoundException message) {
        return () -> new RuntimeException(message);
    }

}
