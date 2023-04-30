package net.bean.java.open.messenger;

import lombok.extern.slf4j.Slf4j;
import net.bean.java.open.messenger.model.User;
import net.bean.java.open.messenger.model.enums.Role;
import net.bean.java.open.messenger.repository.MessageRepository;
import net.bean.java.open.messenger.repository.UserRepository;
import net.bean.java.open.messenger.service.MessageService;
import net.bean.java.open.messenger.service.MessagingManagementService;
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

import java.util.Arrays;
import java.util.List;

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
	CommandLineRunner run(MessagingManagementService messagingManagementService, UserService userService, MessageService messageService, UserRepository userRepository, MessageRepository messageRepository) {
		return args -> {
			userRepository.deleteAll();
			messageRepository.deleteAll();
			messagingManagementService.deleteAllUsers();
			messagingManagementService.deleteAllQueues();

			final String myPassword = "my_password";
			User daniel = createUser(userService, "Daniel", "Silva", myPassword, "avatar_1.png", "Catch every day :D");
			User dominica = createUser(userService, "Dominica", "Rosatti", myPassword, "avatar_2.png", "Today I am out of office");
			User chris = createUser(userService, "Christopher", "Wolf", myPassword, "avatar_3.png", "Party Hard");
			User claudia = createUser(userService, "Claudia", "Wiliams", myPassword, "avatar_4.png", "Sleep Eat Work Repeat");
			User monica = createUser(userService, "Monica", "Rosatti", myPassword, "avatar_5.png", "I love you <3");
		};
	}

	private User createUser(UserService userService, String firstName, String lastName, String password, String avatar, String status) {
		List<Role> roles = List.of(Role.ROLE_USER, Role.ROLE_ADMIN);
		String userName = firstName.toLowerCase() + "." + lastName.toLowerCase();
		User user = userService.saveUser(new User(null, userName, firstName, lastName, password, userName+"@company.com", avatar, status, roles));
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

}
