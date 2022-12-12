package net.bean.java.open.messenger.util;

import java.util.function.Supplier;

public class LazyException {

    public static Supplier<RuntimeException> lazyRuntimeException(String message) {
        return () -> new RuntimeException(message);
    }

}
