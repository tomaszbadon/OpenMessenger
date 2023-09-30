package net.bean.java.open.messenger.rest.resource;

import com.rabbitmq.http.client.Client;
import io.vavr.control.Try;
import net.bean.java.open.messenger.config.RabbitMqConfig;
import net.bean.java.open.messenger.filter.CustomAuthenticationFilter;
import net.bean.java.open.messenger.model.User;
import net.bean.java.open.messenger.rest.model.token.TokensInfo;
import net.bean.java.open.messenger.service.JwtTokenService;
import net.bean.java.open.messenger.service.UserService;
import net.bean.java.open.messenger.util.UserCreator;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AuthenticationResourceTest {

    private final TestRestTemplate restTemplate;

    private final JwtTokenService jwtTokenService;

    private final User user;

    private final String password = "This_Is_Random_PassWord8";

    @MockBean
    private Client client;

    @MockBean
    private RabbitMqConfig.RabbitMqVirtualHost virtualHost;

    @Autowired
    public AuthenticationResourceTest(TestRestTemplate restTemplate, UserService userService, JwtTokenService jwtTokenService) {
        this.restTemplate = restTemplate;
        this.jwtTokenService = jwtTokenService;
        this.user = UserCreator.createUserIfNeeded(userService, "John", "Doe", password, "");
    }

    @Test
    @DisplayName("Authentication Test with JWT")
    public void authenticationTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put(CustomAuthenticationFilter.USERNAME, List.of(user.getUserName()));
        map.put(CustomAuthenticationFilter.PASSWORD, List.of(password));

        ResponseEntity<TokensInfo> responseEntity = restTemplate.exchange(CustomAuthenticationFilter.FILTER_PROCESSES_URL, HttpMethod.POST, new HttpEntity<>(map, headers), TokensInfo.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Try<String> userNameFromToken = jwtTokenService.tryToGetUserName(Try.success(Lists.newArrayList(responseEntity.getBody().getTokens()).get(0).getToken()));
        Assertions.assertEquals(user.getUserName(), userNameFromToken.get());

        userNameFromToken = jwtTokenService.tryToGetUserName(Try.success(Lists.newArrayList(responseEntity.getBody().getTokens()).get(1).getToken()));
        Assertions.assertEquals(user.getUserName(), userNameFromToken.get());
    }

    @Test
    @DisplayName("Invalid authentication Test with JWT")
    public void invalidAuthenticationTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap();
        map.put(CustomAuthenticationFilter.USERNAME, List.of("john.doe"));
        map.put(CustomAuthenticationFilter.PASSWORD, List.of("pa$$word"));

        ResponseEntity<Object> responseEntity = restTemplate.exchange(CustomAuthenticationFilter.FILTER_PROCESSES_URL, HttpMethod.POST, new HttpEntity<>(map, headers), Object.class);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Authorisation Test with JWT")
    public void invalidAuthorisationTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        ResponseEntity<Object> responseEntity = restTemplate.exchange("/api/users/current", HttpMethod.POST, new HttpEntity<>(headers), Object.class);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

}
