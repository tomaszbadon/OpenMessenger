package net.bean.java.open.messenger.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bean.java.open.messenger.model.Message;
import net.bean.java.open.messenger.model.User;
import net.bean.java.open.messenger.repository.MessageRepository;
import net.bean.java.open.messenger.service.NotificationService;
import net.bean.java.open.messenger.util.UserQueueNameProvider;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final RabbitTemplate rabbitTemplate;

    private final UserQueueNameProvider userQueueNameProvider;

    private final MessageRepository messageRepository;

    @Async
    @Override
    public void sendNotification(User recipient, List<Message> messages) {

    }

}
