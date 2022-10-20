package net.bean.java.open.messenger.util;

public class Pause {

    public static void _for(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
