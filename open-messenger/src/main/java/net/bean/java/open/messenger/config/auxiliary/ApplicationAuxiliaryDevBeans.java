package net.bean.java.open.messenger.config.auxiliary;

import lombok.extern.slf4j.Slf4j;
import net.bean.java.open.messenger.model.User;
import net.bean.java.open.messenger.repository.MessageRepository;
import net.bean.java.open.messenger.repository.UserRepository;
import net.bean.java.open.messenger.rest.model.InputMessagePayload;
import net.bean.java.open.messenger.service.MessageService;
import net.bean.java.open.messenger.service.MessagingManagementService;
import net.bean.java.open.messenger.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import static net.bean.java.open.messenger.util.UserCreator.createUserIfNeeded;

@Configuration
@Slf4j
@Profile("dev")
public class ApplicationAuxiliaryDevBeans {

    @Bean
    CommandLineRunner run(MessagingManagementService messagingManagementService, UserService userService, MessageService messageService, UserRepository userRepository, MessageRepository messageRepository) {
        return args -> {
            userRepository.deleteAll();
            messageRepository.deleteAll();
            messagingManagementService.deleteAllUsers();
            messagingManagementService.deleteAllQueues();

            final String myPassword = "my_password";
            User daniel = createUserIfNeeded(userService, "Daniel", "Silva", myPassword);
            User dominica = createUserIfNeeded(userService, "Dominica", "Rosatti", myPassword);
            User chris = createUserIfNeeded(userService, "Christopher", "Wolf", myPassword);
            User claudia = createUserIfNeeded(userService, "Claudia", "Wiliams", myPassword);
            User monica = createUserIfNeeded(userService, "Monica", "Rosatti", myPassword);

            createConversation(messageService, "21-08-2022 17:32:34", chris, dominica, "Hi there");
            createConversation(messageService, "21-08-2022 17:32:50", chris, dominica, "Do you want to go for a coffee with me?");
            createConversation(messageService, "21-08-2022 17:34:34", dominica, chris, "When?");
            createConversation(messageService, "21-08-2022 17:33:34", chris, dominica, "Today at 6pm?");
            createConversation(messageService, "21-08-2022 17:35:34", dominica, chris, "Sure, Let's meet next to the lobby.");

            createConversation(messageService, "23-08-2021 09:32:34", chris, dominica, "Hi Dominica, How are you?");
            createConversation(messageService, "23-08-2021 09:34:23", dominica, chris, "Hi");
            createConversation(messageService, "23-08-2021 09:34:53", dominica, chris, "Everything is fine. Thank you.");
            createConversation(messageService, "23-08-2021 09:35:34", chris, dominica, "I want to ask about the revenue report for Q1");
            createConversation(messageService, "23-08-2021 09:35:45", chris, dominica, "Where can I find it?");
            createConversation(messageService, "24-08-2021 11:08:05", dominica, chris, "Let me check this.");
            createConversation(messageService, "24-08-2021 11:09:17", dominica, chris, "It seems that I haven't uploaded it yet to the server?");
            createConversation(messageService, "25-08-2021 14:12:54", dominica, chris, "Could you give me some minutes to check finish it?");
            createConversation(messageService, "25-08-2021 15:34:34", chris, dominica, "Sure");
            createConversation(messageService, "25-08-2021 08:04:55", dominica, chris, "I will make it in 10 minutes.");

        };
    }

    private void createConversation(MessageService messageService, String sentAt, User sender, User recipient, String message) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        InputMessagePayload messageDTO = new InputMessagePayload();
        messageDTO.setMessage(message);
        messageDTO.setRecipient(recipient.getId());
        messageService.handleNewMessage(messageDTO, format.parse(sentAt), sender);
    }


}
