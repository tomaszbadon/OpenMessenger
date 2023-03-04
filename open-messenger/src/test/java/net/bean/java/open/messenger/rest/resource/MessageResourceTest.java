package net.bean.java.open.messenger.rest.resource;

import net.bean.java.open.messenger.model.entity.User;
import net.bean.java.open.messenger.rest.model.InitialMessagePagesPayload;
import net.bean.java.open.messenger.rest.model.OutputMessagesPayload;
import net.bean.java.open.messenger.rest.model.TokensInfo;
import net.bean.java.open.messenger.rest.util.DummyMessageCreator;
import net.bean.java.open.messenger.service.JwtTokenService;
import net.bean.java.open.messenger.service.UserService;
import net.bean.java.open.messenger.service.implementation.MessageServiceImpl;
import net.bean.java.open.messenger.util.HttpServletRequestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessageResourceTest {

    private final TestRestTemplate restTemplate;

    private final MessageServiceImpl messageServiceV2;

    private final JwtTokenService tokenService;

    private final UserService userService;

    private final User daniel;

    private final User dominica;

    @Autowired
    public MessageResourceTest(TestRestTemplate restTemplate, MessageServiceImpl messageServiceV2, UserService userService, JwtTokenService jwtTokenService) {
        this.restTemplate = restTemplate;
        this.messageServiceV2 = messageServiceV2;
        this.userService = userService;
        this.tokenService = jwtTokenService;
        daniel = userService.getUser("daniel.silva").orElseThrow();
        dominica = userService.getUser("dominica.rosatti").orElseThrow();

        DummyMessageCreator creator = new DummyMessageCreator(messageServiceV2);
        creator.createRead(15, daniel, dominica)
                .createRead(15, dominica, daniel)
                .createUnread(50, daniel, dominica); // 20, 20, 20, 20 -> 1, 2, 3
    }

    @Test
    protected void getLatestMessages() {
        TokensInfo tokensInfo = tokenService.createTokensInfo(dominica, null);
        HttpHeaders headers = new HttpHeaders();
        String url = String.format("/api/users/%s/messages/latest", daniel.getId());
        headers.add(HttpHeaders.AUTHORIZATION, HttpServletRequestUtil.BEARER + tokensInfo.getTokens().stream().findFirst().orElseThrow().getToken());
        ResponseEntity<InitialMessagePagesPayload> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), InitialMessagePagesPayload.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        InitialMessagePagesPayload payload = response.getBody();
        Assertions.assertTrue(payload.getPagesToLoad().contains(1));
        Assertions.assertTrue(payload.getPagesToLoad().contains(2));
        Assertions.assertTrue(payload.getPagesToLoad().contains(3));
    }

    @Test
    protected void getMessages() {
        TokensInfo tokensInfo = tokenService.createTokensInfo(dominica, null);
        HttpHeaders headers = new HttpHeaders();

        String url = String.format("/api/users/%s/messages/0", daniel.getId());
        headers.add(HttpHeaders.AUTHORIZATION, HttpServletRequestUtil.BEARER + tokensInfo.getTokens().stream().findFirst().orElseThrow().getToken());
        ResponseEntity<OutputMessagesPayload> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), OutputMessagesPayload.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        OutputMessagesPayload payload = response.getBody();
        Assertions.assertEquals(20, payload.getMessages().size());
        Assertions.assertEquals(0, payload.getPage());

        url = String.format("/api/users/%s/messages/1", daniel.getId());
        headers.add(HttpHeaders.AUTHORIZATION, HttpServletRequestUtil.BEARER + tokensInfo.getTokens().stream().findFirst().orElseThrow().getToken());
        response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), OutputMessagesPayload.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        payload = response.getBody();
        Assertions.assertEquals(20, payload.getMessages().size());
        Assertions.assertEquals(1, payload.getPage());

        url = String.format("/api/users/%s/messages/2", daniel.getId());
        headers.add(HttpHeaders.AUTHORIZATION, HttpServletRequestUtil.BEARER + tokensInfo.getTokens().stream().findFirst().orElseThrow().getToken());
        response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), OutputMessagesPayload.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        payload = response.getBody();
        Assertions.assertEquals(20, payload.getMessages().size());
        Assertions.assertEquals(2, payload.getPage());

        url = String.format("/api/users/%s/messages/3", daniel.getId());
        headers.add(HttpHeaders.AUTHORIZATION, HttpServletRequestUtil.BEARER + tokensInfo.getTokens().stream().findFirst().orElseThrow().getToken());
        response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), OutputMessagesPayload.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        payload = response.getBody();
        Assertions.assertEquals(20, payload.getMessages().size());
        Assertions.assertEquals(3, payload.getPage());
    }


}
