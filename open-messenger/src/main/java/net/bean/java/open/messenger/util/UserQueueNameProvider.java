package net.bean.java.open.messenger.util;

import net.bean.java.open.messenger.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserQueueNameProvider {

    private final String PREFIX = "user-queue-";

    public String createQueueName(User user) {
        return PREFIX + user.getUserName();
    }

}
