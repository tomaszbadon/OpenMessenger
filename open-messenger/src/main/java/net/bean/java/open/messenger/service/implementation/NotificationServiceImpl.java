package net.bean.java.open.messenger.service.implementation;

import lombok.RequiredArgsConstructor;
import net.bean.java.open.messenger.model.Message;
import net.bean.java.open.messenger.rest.model.Notification;
import net.bean.java.open.messenger.service.NotificationSerivce;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationSerivce {

    private final static String DESTINATION = "/queue/new";

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void notifyUser(Message message) {
        throw new UnsupportedOperationException();

    }

    @Override
    public List<Notification> getNotification(long userId) {
        throw new UnsupportedOperationException();
    }
}
