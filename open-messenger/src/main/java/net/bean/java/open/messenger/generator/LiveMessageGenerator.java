package net.bean.java.open.messenger.generator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bean.java.open.messenger.model.Message;
import net.bean.java.open.messenger.rest.model.InputMessagePayload;
import net.bean.java.open.messenger.model.User;
import net.bean.java.open.messenger.rest.model.OutputMessagePayload;
import net.bean.java.open.messenger.service.MessageService;
import net.bean.java.open.messenger.service.NotificationService;
import net.bean.java.open.messenger.service.UserService;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
@Profile("dev")
public class LiveMessageGenerator {

    private final UserService userService;
    private final MessageService messageService;
    private final NotificationService notificationService;

    private final Random random = new Random();

    private List<String> greetings = List.of(
        "Hi!",
        "Hey, Heya or Hey there!",
        "Morning!",
        "How are things?",
        "What’s new?",
        "It’s good to see you",
        "G’day!",
        "Howdy!",
        "What’s up?",
        "How’s it going?",
        "What’s happening",
        "What’s the story?",
        "Yo!"
    );

    @Scheduled(fixedRate = 3000, initialDelayString = "5000")
    public void reportCurrentTime() {
        User recipient = userService.getUserByUserName("dominica.rosatti").get();
        List<User> users = userService.getUsers().stream().filter((u) -> u.getId() != recipient.getId()).collect(Collectors.toList());
        User sender = users.get(random.nextInt(users.size()));
        //User sender = userService.getUser("christopher.wolf").get();
        String message = greetings.get(random.nextInt(greetings.size()));
        InputMessagePayload inputMessagePayload = new InputMessagePayload();
        inputMessagePayload.setMessage(message);
        inputMessagePayload.setRecipient(recipient.getId());
        OutputMessagePayload savedMessage = messageService.handleNewMessage(inputMessagePayload, new Date(), sender);
        //savedMessage = messageService.getMessageById(savedMessage.getId());
        //notificationSerivce.notifyUser(savedMessage);
        log.info("A message from user {} was sent", sender.getUserName());
    }

}
