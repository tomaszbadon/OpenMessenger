package net.bean.java.open.messenger.service.implementation;

import net.bean.java.open.messenger.model.entity.User;
import net.bean.java.open.messenger.repository.MessageMongoDbRepository;
import net.bean.java.open.messenger.rest.model.InitPagesPayload;
import net.bean.java.open.messenger.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MessageServiceV2ImplTest {

    private final String DUMMY_TOKEN = "TOKEN";

    @Mock
    private UserService userService;

    @Mock
    private CurrentUserServiceImpl currentUserService;

    @Mock
    private MessageMongoDbRepository messageRepository;

    @InjectMocks
    private MessageServiceV2Impl messageService;

    @Test
    @DisplayName("It tests getting the latest pages when there are no messages")
    protected void getLatestPagesToLoadWhenNoMessages() {
        messageService.setNumberOfMessagesPerPage("20");

        User currentUser = new User();
        currentUser.setId(5L);
        when(currentUserService.getUserFromTokenOrElseThrowException(anyString())).thenReturn(currentUser);

        User user = new User();
        user.setId(6L);
        when(userService.getUserOrElseThrowException(anyLong())).thenReturn(user);
        when(messageRepository.countByConversationId(anyString())).thenReturn(0L);

        InitPagesPayload initPagesPayload = messageService.getLatestPagesToLoad(DUMMY_TOKEN, 6L);
        Assertions.assertEquals(0, initPagesPayload.getPagesToLoad().size());
    }

    @Test
    @DisplayName("It tests getting the latest page when there are only a few messages")
    protected void getLatestPagesToLoadWhenThereAreFewMessages() {
        messageService.setNumberOfMessagesPerPage("20");

        User currentUser = new User();
        currentUser.setId(5L);
        when(currentUserService.getUserFromTokenOrElseThrowException(anyString())).thenReturn(currentUser);

        User user = new User();
        user.setId(6L);
        when(userService.getUserOrElseThrowException(anyLong())).thenReturn(user);

        when(messageRepository.countByConversationId(anyString())).thenReturn(20L);
        when(messageRepository.countByIsReadAndConversationId(anyBoolean(), anyString())).thenReturn(0L);

        InitPagesPayload initPagesPayload = messageService.getLatestPagesToLoad(DUMMY_TOKEN, 6L);
        Assertions.assertEquals(1, initPagesPayload.getPagesToLoad().size());
        Assertions.assertEquals(0, initPagesPayload.getPagesToLoad().get(0));

    }

    @Test
    @DisplayName("It tests getting the latest pages when all messages are read")
    protected void getLatestPagesToLoadWhenAllIsRead() {
        messageService.setNumberOfMessagesPerPage("20");

        User currentUser = new User();
        currentUser.setId(5L);
        when(currentUserService.getUserFromTokenOrElseThrowException(anyString())).thenReturn(currentUser);

        User user = new User();
        user.setId(6L);
        when(userService.getUserOrElseThrowException(anyLong())).thenReturn(user);

        when(messageRepository.countByConversationId(anyString())).thenReturn(20L);
        when(messageRepository.countByIsReadAndConversationId(anyBoolean(), anyString())).thenReturn(0L);

        InitPagesPayload initPagesPayload = messageService.getLatestPagesToLoad(DUMMY_TOKEN, 6L);
        Assertions.assertEquals(1, initPagesPayload.getPagesToLoad().size());
        Assertions.assertEquals(0, initPagesPayload.getPagesToLoad().get(0));
    }

    @Test
    @DisplayName("It tests getting the latest pages when there are a few unread messages ")
    protected void getLatestPagesToLoadWhenThereAreFewUnreadMessages() {
        messageService.setNumberOfMessagesPerPage("20");

        User currentUser = new User();
        currentUser.setId(5L);
        when(currentUserService.getUserFromTokenOrElseThrowException(anyString())).thenReturn(currentUser);

        User user = new User();
        user.setId(6L);
        when(userService.getUserOrElseThrowException(anyLong())).thenReturn(user);

        when(messageRepository.countByConversationId(anyString())).thenReturn(50L);
        when(messageRepository.countByIsReadAndConversationId(anyBoolean(), anyString())).thenReturn(5L);

        InitPagesPayload initPagesPayload = messageService.getLatestPagesToLoad(DUMMY_TOKEN, 6L);
        Assertions.assertEquals(1, initPagesPayload.getPagesToLoad().size());
        Assertions.assertEquals(2, initPagesPayload.getPagesToLoad().get(0));
    }

    @Test
    @DisplayName("It tests getting the latest pages when there are many unread messages")
    protected void getLatestPagesToLoadWhenThereAreManyUnreadMessages() {
        messageService.setNumberOfMessagesPerPage("20");

        User currentUser = new User();
        currentUser.setId(5L);
        when(currentUserService.getUserFromTokenOrElseThrowException(anyString())).thenReturn(currentUser);

        User user = new User();
        user.setId(6L);
        when(userService.getUserOrElseThrowException(anyLong())).thenReturn(user);

        when(messageRepository.countByConversationId(anyString())).thenReturn(110L);
        when(messageRepository.countByIsReadAndConversationId(anyBoolean(), anyString())).thenReturn(50L);

        InitPagesPayload initPagesPayload = messageService.getLatestPagesToLoad(DUMMY_TOKEN, 6L);
        Assertions.assertEquals(3, initPagesPayload.getPagesToLoad().size());
        Assertions.assertTrue(initPagesPayload.getPagesToLoad().contains(5L));
        Assertions.assertTrue(initPagesPayload.getPagesToLoad().contains(4L));
        Assertions.assertTrue(initPagesPayload.getPagesToLoad().contains(3L));

    }

}
