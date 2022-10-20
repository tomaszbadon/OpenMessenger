package net.bean.java.open.messenger.service;

import lombok.RequiredArgsConstructor;
import net.bean.java.open.messenger.data.dto.Notification;
import net.bean.java.open.messenger.data.jpa.model.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements  NotificationSerivce {

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
