package net.bean.java.open.messenger.service.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bean.java.open.messenger.model.Message;
import net.bean.java.open.messenger.model.User;
import net.bean.java.open.messenger.model.notification.Notification;
import net.bean.java.open.messenger.model.notification.NotificationMessage;
import net.bean.java.open.messenger.repository.MessageRepository;
import net.bean.java.open.messenger.service.NotificationService;
import net.bean.java.open.messenger.util.UserQueueNameProvider;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile({"prod", "dev"})
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final RabbitTemplate rabbitTemplate;

    private final UserQueueNameProvider userQueueNameProvider;

    private final MessageRepository messageRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void sendNotificationToUser(User toUser) {
        try {
            List<Message> unReadMessages = messageRepository.findByRecipientIdAndIsRead(toUser.getId(), false, Message.CONVERSATION_ID, Message.SENDER_ID);
            Notification notification = new Notification(unReadMessages.stream().map(m -> new NotificationMessage(m.getId(), m.getConversationId())).toList());
            String qName = userQueueNameProvider.createQueueName(toUser);
            rabbitTemplate.convertAndSend(qName, objectMapper.writeValueAsString(notification));
        } catch (JsonProcessingException | AmqpException e) {
            log.error("Cannot send notification to a user because of: " + e.getMessage(), e);
        }
    }

}
