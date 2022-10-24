package net.bean.java.open.messenger;

import lombok.extern.slf4j.Slf4j;
import net.bean.java.open.messenger.data.dto.InputMessageDTO;
import net.bean.java.open.messenger.data.jpa.model.Role;
import net.bean.java.open.messenger.data.jpa.model.User;
import net.bean.java.open.messenger.data.mapper.MessageMapper;
import net.bean.java.open.messenger.service.MessageService;
import net.bean.java.open.messenger.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
@EnableScheduling
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

			/*
			createConversation(messageService, "13-02-2021 17:32:56", chris, dominica, "However, this bright idea to just replace one tired snowmobile with a brand new snowmobile, you know, to free up the snowmobile parking lot for other vehicles, never worked.");
			createConversation(messageService, "13-02-2021 17:32:43", chris, dominica, "It was not a huge issue until the warm up started last week.");
			createConversation(messageService, "13-02-2021 17:33:11", dominica, chris, "On Friday, we had four big inches of snow.");
			createConversation(messageService, "13-02-2021 17:33:39", dominica, chris, "Sunday was dry.");
			createConversation(messageService, "14-03-2021 08:32:34", chris, dominica, "By Monday, snow began to fall again.");
			createConversation(messageService, "14-03-2021 08:34:34", dominica, chris, "The snowmobile parking lot was now incredibly busy.");
			createConversation(messageService, "14-03-2021 08:34:44", dominica, chris, "When I went to pick up my snowmobile from the shop, it was bumper to bumper and six people deep at the front.");
			createConversation(messageService, "14-03-2022 08:35:34", chris, dominica, "The back lot was even worse.");
			createConversation(messageService, "14-03-2021 08:36:34", chris, dominica, "I was now even more thankful for the snowmobile that had been at my house.");
			createConversation(messageService, "14-03-2021 08:37:34", chris, dominica, "Wednesday morning, at 10: 30, I started having a hard time driving to work.");
			createConversation(messageService, "14-03-2021 17:38:34", chris, dominica, "I had to slow down to 35 mph to inch my way along the road.");
			createConversation(messageService, "18-03-2021 19:12:34", dominica, chris, "Even the northbound side was backed up and cars were passing me going the opposite way.");
			createConversation(messageService, "18-03-2021 19:12:55", chris, dominica, "The highway patrol had set up a rope barrier, opening two lanes at a time, to allow traffic to move north.");
			createConversation(messageService, "18-03-2021 19:13:19", dominica, chris, "I had never seen anything like it.");
			createConversation(messageService, "18-03-2021 19:13:21", chris, dominica, "I later found out that they were expecting a quarter of an inch of snow an hour until noon.");
			createConversation(messageService, "18-03-2021 19:14:34", chris, dominica, "It was not until 1: 00 when I got to the shop that I saw anything other than a four wheel drive SUV.");


			createConversation(messageService, "13-02-2022 17:32:56", chris, dominica, "However, this bright idea to just replace one tired snowmobile with a brand new snowmobile, you know, to free up the snowmobile parking lot for other vehicles, never worked.");
			createConversation(messageService, "13-02-2022 17:32:43", chris, dominica, "It was not a huge issue until the warm up started last week.");
			createConversation(messageService, "13-02-2022 17:33:11", dominica, chris, "On Friday, we had four big inches of snow.");
			createConversation(messageService, "13-02-2022 17:33:39", dominica, chris, "Sunday was dry.");
			createConversation(messageService, "14-03-2022 08:32:34", chris, dominica, "By Monday, snow began to fall again.");
			createConversation(messageService, "14-03-2022 08:34:34", dominica, chris, "The snowmobile parking lot was now incredibly busy.");
			createConversation(messageService, "14-03-2022 08:34:44", dominica, chris, "When I went to pick up my snowmobile from the shop, it was bumper to bumper and six people deep at the front.");
			createConversation(messageService, "14-03-2022 08:35:34", chris, dominica, "The back lot was even worse.");
			createConversation(messageService, "14-03-2022 08:36:34", chris, dominica, "I was now even more thankful for the snowmobile that had been at my house.");
			createConversation(messageService, "14-03-2022 08:37:34", chris, dominica, "Wednesday morning, at 10: 30, I started having a hard time driving to work.");
			createConversation(messageService, "14-03-2022 17:38:34", chris, dominica, "I had to slow down to 35 mph to inch my way along the road.");
			createConversation(messageService, "18-03-2022 19:12:34", dominica, chris, "Even the northbound side was backed up and cars were passing me going the opposite way.");
			createConversation(messageService, "18-03-2022 19:12:55", chris, dominica, "The highway patrol had set up a rope barrier, opening two lanes at a time, to allow traffic to move north.");
			createConversation(messageService, "18-03-2022 19:13:19", dominica, chris, "I had never seen anything like it.");
			createConversation(messageService, "18-03-2022 19:13:21", chris, dominica, "I later found out that they were expecting a quarter of an inch of snow an hour until noon.");
			createConversation(messageService, "18-03-2022 19:14:34", chris, dominica, "It was not until 1: 00 when I got to the shop that I saw anything other than a four wheel drive SUV.");

			createConversation(messageService, "20-03-2021 19:13:19", dominica, chris, "I had never seen anything like it.");
			createConversation(messageService, "20-03-2021 19:13:21", chris, dominica, "I later found out that they were expecting a quarter of an inch of snow an hour until noon.");
			createConversation(messageService, "20-03-2021 19:14:34", chris, dominica, "It was not until 1: 00 when I got to the shop that I saw anything other than a four wheel drive SUV.");


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
			*/
		};
	}

	private void createConversation(MessageService messageService, String sentAt, User from, User to, String message) throws ParseException {
		InputMessageDTO messageDTO = new InputMessageDTO();
		messageDTO.setMessage(message);
		messageDTO.setRecipient(to.getId());
		messageService.saveMessageWithSpecificDate(messageDTO, from.getId(), sentAt);
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
