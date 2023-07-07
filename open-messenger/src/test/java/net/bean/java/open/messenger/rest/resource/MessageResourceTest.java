package net.bean.java.open.messenger.rest.resource;

import net.bean.java.open.messenger.model.User;
import net.bean.java.open.messenger.repository.MessageRepository;
import net.bean.java.open.messenger.rest.model.InputMessagePayload;
import net.bean.java.open.messenger.rest.model.OutputMessagePayload;
import net.bean.java.open.messenger.rest.model.OutputMessagesPayload;
import net.bean.java.open.messenger.rest.model.TokensInfo;
import net.bean.java.open.messenger.service.JwtTokenService;
import net.bean.java.open.messenger.service.UserService;
import net.bean.java.open.messenger.service.implementation.MessageServiceImpl;
import net.bean.java.open.messenger.util.HttpServletRequestUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessageResourceTest {

    private final TestRestTemplate restTemplate;

    private final MessageServiceImpl messageService;

    private final JwtTokenService tokenService;

    private final UserService userService;

    private final MessageRepository messageRepository;

    private final User daniel;

    private final User dominica;

    private final User monica;

    @Autowired
    public MessageResourceTest(TestRestTemplate restTemplate, MessageServiceImpl messageService, JwtTokenService tokenService, UserService userService, MessageRepository messageRepository) {
        this.restTemplate = restTemplate;
        this.messageService = messageService;
        this.tokenService = tokenService;
        this.userService = userService;
        this.messageRepository = messageRepository;
        daniel = userService.getUserByUserName("daniel.silva").orElseThrow();
        dominica = userService.getUserByUserName("dominica.rosatti").orElseThrow();
        monica = userService.getUserByUserName("monica.rosatti").orElseThrow();
    }

    @AfterEach
    protected void cleanUpRepository() {
        messageRepository.deleteAll();
    }


    @Test
    void postMessageAndRead() {
        final String message = "This is a message";
        InputMessagePayload body = new InputMessagePayload();
        body.setMessage(message);
        body.setRecipient(daniel.getId());

        TokensInfo dominicaTokensInfo = tokenService.createTokensInfo(dominica, null);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, HttpServletRequestUtil.BEARER + dominicaTokensInfo.getTokens().stream().findFirst().orElseThrow().getToken());
        ResponseEntity<OutputMessagePayload> postResponse = restTemplate.exchange("/api/messages", HttpMethod.POST, new HttpEntity<>(body, headers), OutputMessagePayload.class);
        Assertions.assertTrue(postResponse.getStatusCode() == HttpStatus.CREATED);

        TokensInfo danielTokenInfo = tokenService.createTokensInfo(daniel, null);
        headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, HttpServletRequestUtil.BEARER + danielTokenInfo.getTokens().stream().findFirst().orElseThrow().getToken());
        String url = String.format("/api/users/%s/messages/0", dominica.getId());
        ResponseEntity<OutputMessagesPayload> getResponse = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), OutputMessagesPayload.class);
        OutputMessagesPayload outputMessagesPayload = getResponse.getBody();
        Assertions.assertTrue(getResponse.getStatusCode() == HttpStatus.OK);
        Assertions.assertTrue(outputMessagesPayload.getMessages().size() == 1);
        OutputMessagePayload outputMessagePayload = outputMessagesPayload.getMessages().stream().findFirst().get();
        Assertions.assertEquals(message, outputMessagePayload.getMessage());
        Assertions.assertEquals(false, outputMessagePayload.isRead());
        Assertions.assertEquals(daniel.getId(), outputMessagePayload.getRecipient());
        Assertions.assertEquals(dominica.getId(), outputMessagePayload.getSender());
    }

    @Test
    void postMessageAndReadWithNoPermissionException() {
        final String message = "This is a message";
        InputMessagePayload body = new InputMessagePayload();
        body.setMessage(message);
        body.setRecipient(daniel.getId());

        TokensInfo dominicaTokensInfo = tokenService.createTokensInfo(dominica, null);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, HttpServletRequestUtil.BEARER + dominicaTokensInfo.getTokens().stream().findFirst().orElseThrow().getToken());
        ResponseEntity<OutputMessagePayload> postResponse = restTemplate.exchange("/api/messages", HttpMethod.POST, new HttpEntity<>(body, headers), OutputMessagePayload.class);
        Assertions.assertTrue(postResponse.getStatusCode() == HttpStatus.CREATED);
        Assertions.assertTrue(StringUtils.isNotBlank(postResponse.getBody().getId()));
        String messageId = postResponse.getBody().getId();


        TokensInfo monicaTokenInfo = tokenService.createTokensInfo(monica, null);
        headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, HttpServletRequestUtil.BEARER + monicaTokenInfo.getTokens().stream().findFirst().orElseThrow().getToken());
        String url = String.format("/api/messages/%s", messageId);
        ResponseEntity<OutputMessagesPayload> getResponse = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), OutputMessagesPayload.class);
        Assertions.assertTrue(getResponse.getStatusCode().equals(HttpStatus.FORBIDDEN));
    }

}
