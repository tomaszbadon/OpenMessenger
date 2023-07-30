package net.bean.java.open.messenger.service.implementation;

import net.bean.java.open.messenger.model.User;
import net.bean.java.open.messenger.service.NotificationService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"test"})
public class DummyNotificationServiceImpl implements NotificationService {

    @Override
    public void sendNotificationToUser(User to) {

    }
}
