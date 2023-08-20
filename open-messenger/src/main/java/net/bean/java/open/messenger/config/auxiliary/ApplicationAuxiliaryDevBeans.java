package net.bean.java.open.messenger.config.auxiliary;

import lombok.extern.slf4j.Slf4j;
import net.bean.java.open.messenger.model.User;
import net.bean.java.open.messenger.repository.MessageRepository;
import net.bean.java.open.messenger.repository.UserRepository;
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
        };
    }

    @Bean
    public CorsFilter corsFilter() {
        log.info("Core Filter activated");
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
                "Accept", "Authorization", "Origin, Accept", "X-Requested-With",
                "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
                "Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

}
