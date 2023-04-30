package net.bean.java.open.messenger.generator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bean.java.open.messenger.model.User;
import net.bean.java.open.messenger.service.UserService;
import net.bean.java.open.messenger.util.UserQueueNameProvider;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Slf4j
@Component
public class MessageSender {

    private final RabbitTemplate template;

    private final UserQueueNameProvider userQueueNameProvider;

    @Autowired
    private final UserService userService;

    @Scheduled(fixedRate = 3000, initialDelayString = "5000")
    public void send() {
        List<User> users = userService.getUsers();
        Random r = new Random();
        int index = r.nextInt(users.size());
        User user = users.get(index);
        user = userService.getUserByUserName("dominica.rosatti").get();
        String queue = userQueueNameProvider.createQueueName(user);
        log.info("Sent a message to user: " + user.getUserName() + " to queue: " + queue);
        template.convertAndSend(queue, String.valueOf(System.currentTimeMillis()));
    }

}
