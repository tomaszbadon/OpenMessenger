package net.bean.java.open.messenger.rest.resource;

import net.bean.java.open.messenger.model.entity.User;
import net.bean.java.open.messenger.rest.model.TokensInfo;
import net.bean.java.open.messenger.rest.util.DummyMessageCreator;
import net.bean.java.open.messenger.service.JwtTokenService;
import net.bean.java.open.messenger.service.UserService;
import net.bean.java.open.messenger.service.implementation.MessageServiceV2Impl;
import net.bean.java.open.messenger.util.HttpServletRequestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessageResourceTest {

    private final TestRestTemplate restTemplate;

    private final MessageServiceV2Impl messageServiceV2;

    private final JwtTokenService tokenService;

    private final UserService userService;

    private final User daniel;

    private final User dominica;

    @Autowired
    public MessageResourceTest(TestRestTemplate restTemplate, MessageServiceV2Impl messageServiceV2, UserService userService, JwtTokenService jwtTokenService) {
        this.restTemplate = restTemplate;
        this.messageServiceV2 = messageServiceV2;
        this.userService = userService;
        this.tokenService = jwtTokenService;
        daniel = userService.getUser("daniel.silva").orElseThrow();
        dominica = userService.getUser("dominica.rosatti").orElseThrow();
    }

    @Test
    protected void test1() {
        DummyMessageCreator creator = new DummyMessageCreator(messageServiceV2);
        creator.createRead(12, daniel, dominica);
        TokensInfo tokensInfo = tokenService.createTokensInfo(dominica, null);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, HttpServletRequestUtil.BEARER + tokensInfo.getTokens().stream().findFirst().orElseThrow().getToken());
        ResponseEntity<Object> response = restTemplate.exchange("/api/messages/" + daniel.getId(), HttpMethod.GET, new HttpEntity<>(headers), Object.class);
        response.getStatusCode();
    }


}
