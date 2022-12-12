package net.bean.java.open.messenger.service.impl;

import lombok.RequiredArgsConstructor;
import net.bean.java.open.messenger.entity.Notification;
import net.bean.java.open.messenger.model.jpa.Message;
import net.bean.java.open.messenger.service.MessageService;
import net.bean.java.open.messenger.service.NotificationSerivce;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationSerivce {

    private final static String DESTINATION = "/queue/new";

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageService messageService;

    @Override
    public void notifyUser(Message message) {
        Notification notification = new Notification(message.getSender().getId(), message.getId());
        simpMessagingTemplate.convertAndSendToUser(message.getRecipient().getUserName(), DESTINATION, notification);
    }

    @Override
    public List<Notification> getNotification(long userId) {
        List<Message> messages = messageService.getUnacknowledgedMessages(userId);
        return messages.stream().map(m -> new Notification(m.getSender().getId(), m.getId())).collect(Collectors.toList());
    }
}
