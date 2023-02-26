package net.bean.java.open.messenger.rest.resource;

import io.vavr.control.Try;
import net.bean.java.open.messenger.filter.CustomAuthenticationFilter;
import net.bean.java.open.messenger.model.entity.User;
import net.bean.java.open.messenger.rest.model.TokensInfo;
import net.bean.java.open.messenger.service.JwtTokenService;
import net.bean.java.open.messenger.service.UserService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationResourceTest {

    private final TestRestTemplate restTemplate;

    private final JwtTokenService jwtTokenService;

    private final User user;

    private final String password = "This_Is_Random_PassWord8";

    @Autowired
    public AuthenticationResourceTest(TestRestTemplate restTemplate, UserService userService, JwtTokenService jwtTokenService) {
        this.restTemplate = restTemplate;
        this.jwtTokenService = jwtTokenService;
        user = new User();
        user.setUserName("john.doe");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword(password);
        user.setEmail("j.doe@acke.com");
        userService.saveUser(user);
    }

    @Test
    @DisplayName("Authentication Test with JWT")
    public void authenticationTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap();
        map.put(CustomAuthenticationFilter.USERNAME, List.of(user.getUserName()));
        map.put(CustomAuthenticationFilter.PASSWORD, List.of(password));

        ResponseEntity<TokensInfo> responseEntity = restTemplate.exchange("/login", HttpMethod.POST, new HttpEntity<>(map, headers), TokensInfo.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Try<String> userNameFromToken = jwtTokenService.getUserName(Lists.newArrayList(responseEntity.getBody().getTokens()).get(0).getToken());
        Assertions.assertEquals(user.getUserName(), userNameFromToken.get());

        userNameFromToken = jwtTokenService.getUserName(Lists.newArrayList(responseEntity.getBody().getTokens()).get(1).getToken());
        Assertions.assertEquals(user.getUserName(), userNameFromToken.get());
    }

}
