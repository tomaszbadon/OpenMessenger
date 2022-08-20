package net.bean.java.open.messenger;

import lombok.extern.slf4j.Slf4j;
import net.bean.java.open.messenger.data.dto.MessageDTO;
import net.bean.java.open.messenger.data.mapper.MessageMapper;
import net.bean.java.open.messenger.data.jpa.model.Role;
import net.bean.java.open.messenger.data.jpa.model.User;
import net.bean.java.open.messenger.service.MessageService;
import net.bean.java.open.messenger.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
@Slf4j
public class OpenMessengerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenMessengerApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService, MessageService messageService) throws ParseException {
		return args -> {
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_MANAGER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));

			User daniel = createUser(userService, "Daniel", "Silva", "my_password", "avatar_1.png", "Catch every day :D");
			User dominica = createUser(userService, "Dominica", "Rosatti", "my_password", "avatar_2.png", "Today I am out of office");
			User chris = createUser(userService, "Christopher", "Wolf", "my_password", "avatar_3.png", "Party Hard");
			User claudia = createUser(userService, "Claudia", "Wiliams", "my_password", "avatar_4.png", "Sleep Eat Work Repeat");
			User monica = createUser(userService, "Monica", "Rosatti", "my_password", "avatar_5.png", "I love you <3");

			createConversation(messageService, "21-08-2022 17:32:34", chris, dominica, "Hi there");
			createConversation(messageService, "21-08-2022 17:32:50", chris, dominica, "Do you want to go for a coffee with me?");
			createConversation(messageService, "21-08-2022 17:34:34", dominica, dominica, "When?");
			createConversation(messageService, "21-08-2022 17:33:34", chris, dominica, "Today at 6pm?");
			createConversation(messageService, "21-08-2022 17:35:34", dominica, chris, "Sure, Let's meet next to the lobby.");

			createConversation(messageService, "23-08-2022 09:32:34", chris, dominica, "Hi Dominica, How are you?");
			createConversation(messageService, "23-08-2022 09:34:23", dominica, chris, "Hi");
			createConversation(messageService, "23-08-2022 09:34:53", dominica, chris, "Everything is fine. Thank you.");
			createConversation(messageService, "23-08-2022 09:35:34", chris, dominica, "I want to ask about the revenue report for Q1");
			createConversation(messageService, "23-08-2022 09:35:45", chris, dominica, "Where can I find it?");
			createConversation(messageService, "24-08-2022 11:08:05", dominica, chris, "Let me check this.");
			createConversation(messageService, "24-08-2022 11:09:17", dominica, chris, "It seems that I haven't uploaded it yet to the server?");
			createConversation(messageService, "25-08-2022 14:12:54", dominica, chris, "Could you give me some minutes to check finish it?");
			createConversation(messageService, "25-08-2022 15:34:34", chris, dominica, "Sure");
			createConversation(messageService, "25-08-2022 08:04:55", dominica, chris, "I will make it in 10 minutes.");
		};
	}

	private void createConversation(MessageService messageService, String sentAt, User from, User to, String message) throws ParseException {
		MessageDTO messageDTO = new MessageDTO();
		messageDTO.setMessage(message);
		messageDTO.setSender(from.getId());
		messageDTO.setRecipient(to.getId());
		messageDTO.setSentAt(sentAt);
		messageService.saveMessageWithSpecificDate(messageDTO);
	}

	private User createUser(UserService userService, String firstName, String lastName, String password, String avatar, String status) {
		String userName = firstName.toLowerCase() + "." + lastName.toLowerCase();
		User user = userService.saveUser(new User(null, userName, firstName, lastName, password, userName+"@company.com", avatar, status, new ArrayList<>()));
		userService.addRoleToUser(userName, "ROLE_USER");
		userService.addRoleToUser(userName, "ROLE_ADMIN");
		return user;
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

	@Bean
	public MessageMapper messageMapper() {
		return new MessageMapper();
	}

}
