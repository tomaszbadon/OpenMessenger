package net.bean.java.open.messenger.rest.resource;

import net.bean.java.open.messenger.model.User;
import net.bean.java.open.messenger.repository.MessageRepository;
import net.bean.java.open.messenger.rest.model.InitialMessagePagesPayload;
import net.bean.java.open.messenger.rest.model.OutputMessagesPayload;
import net.bean.java.open.messenger.rest.model.TokensInfo;
import net.bean.java.open.messenger.rest.util.DummyMessageCreator;
import net.bean.java.open.messenger.service.JwtTokenService;
import net.bean.java.open.messenger.service.UserService;
import net.bean.java.open.messenger.service.implementation.MessageServiceImpl;
import net.bean.java.open.messenger.util.HttpServletRequestUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class MessageResourceTestWithDummyData {

    private final TestRestTemplate restTemplate;

    private final MessageServiceImpl messageService;

    private final JwtTokenService tokenService;

    private final UserService userService;

    private final MessageRepository messageRepository;

    private final User daniel;

    private final User dominica;

    @Autowired
    public MessageResourceTestWithDummyData(TestRestTemplate restTemplate, MessageServiceImpl messageService, UserService userService, MessageRepository messageRepository, JwtTokenService jwtTokenService) {
        this.restTemplate = restTemplate;
        this.messageService = messageService;
        this.userService = userService;
        this.tokenService = jwtTokenService;
        this.messageRepository = messageRepository;
        daniel = userService.getUserByUserName("daniel.silva").orElseThrow();
        dominica = userService.getUserByUserName("dominica.rosatti").orElseThrow();
    }

    @BeforeEach
    protected void populateRepositoryWithMessages() {
        DummyMessageCreator creator = new DummyMessageCreator(messageService);
        creator.createRead(15, daniel, dominica)
                .createRead(15, dominica, daniel)
                .createUnread(50, daniel, dominica);
    }

    @AfterEach
    protected void cleanUpRepository() {
        messageRepository.deleteAll();
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
