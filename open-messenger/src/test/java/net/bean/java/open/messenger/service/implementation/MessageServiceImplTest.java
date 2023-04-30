package net.bean.java.open.messenger.service.implementation;

import io.vavr.control.Try;
import net.bean.java.open.messenger.model.User;
import net.bean.java.open.messenger.repository.MessageRepository;
import net.bean.java.open.messenger.rest.model.InitialMessagePagesPayload;
import net.bean.java.open.messenger.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MessageServiceImplTest {

    private final Try<String> DUMMY_TOKEN = Try.success("TOKEN");

    @Mock
    private UserService userService;

    @Mock
    private CurrentUserServiceImpl currentUserService;

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private MessageServiceImpl messageService;

    @Test
    @DisplayName("It tests getting the latest pages when there are no messages")
    protected void getLatestPagesToLoadWhenNoMessages() {
        messageService.setNumberOfMessagesPerPage(20);

        User currentUser = new User();
        currentUser.setId("DUMMY_ID");
        when(currentUserService.tryToGetUserFromToken((Try<String>) any())).thenReturn(Try.success(currentUser));

        User user = new User();
        user.setId("DUMMY_ID");
        when(userService.tryToGetUserById(anyString())).thenReturn(Try.success(user));
        when(messageRepository.countByConversationId(anyString())).thenReturn(0L);

        InitialMessagePagesPayload initialMessagePagesPayload = messageService.getLatestPagesToLoad(DUMMY_TOKEN, "DUMMY_ID");
        Assertions.assertEquals(0, initialMessagePagesPayload.getPagesToLoad().size());
    }

    @Test
    @DisplayName("It tests getting the latest page when there are only a few messages")
    protected void getLatestPagesToLoadWhenThereAreFewMessages() {
        messageService.setNumberOfMessagesPerPage(20);

        User currentUser = new User();
        currentUser.setId("DUMMY_ID");
        when(currentUserService.tryToGetUserFromToken((Try<String>) any())).thenReturn(Try.success(currentUser));

        User user = new User();
        user.setId("DUMMY_ID");
        when(userService.tryToGetUserById(anyString())).thenReturn(Try.success(user));

        when(messageRepository.countByConversationId(anyString())).thenReturn(20L);
        when(messageRepository.countByRecipientIdAndIsReadAndConversationId(anyString(), anyBoolean(), anyString())).thenReturn(0L);

        InitialMessagePagesPayload initialMessagePagesPayload = messageService.getLatestPagesToLoad(DUMMY_TOKEN, "DUMMY_ID");
        Assertions.assertEquals(1, initialMessagePagesPayload.getPagesToLoad().size());
        Assertions.assertEquals(0, initialMessagePagesPayload.getPagesToLoad().get(0));

    }

    @Test
    @DisplayName("It tests getting the latest pages when all messages are read")
    protected void getLatestPagesToLoadWhenAllIsRead() {
        messageService.setNumberOfMessagesPerPage(20);

        User currentUser = new User();
        currentUser.setId("DUMMY_ID");
        when(currentUserService.tryToGetUserFromToken((Try<String>) any())).thenReturn(Try.success(currentUser));

        User user = new User();
        user.setId("DUMMY_ID");
        when(userService.tryToGetUserById(anyString())).thenReturn(Try.success(user));

        when(messageRepository.countByConversationId(anyString())).thenReturn(20L);
        when(messageRepository.countByRecipientIdAndIsReadAndConversationId(anyString(), anyBoolean(), anyString())).thenReturn(0L);

        InitialMessagePagesPayload initialMessagePagesPayload = messageService.getLatestPagesToLoad(DUMMY_TOKEN, "DUMMY_ID");
        Assertions.assertEquals(1, initialMessagePagesPayload.getPagesToLoad().size());
        Assertions.assertEquals(0, initialMessagePagesPayload.getPagesToLoad().get(0));
    }

    @Test
    @DisplayName("It tests getting the latest pages when there are a few unread messages ")
    protected void getLatestPagesToLoadWhenThereAreFewUnreadMessages() {
        messageService.setNumberOfMessagesPerPage(20);

        User currentUser = new User();
        currentUser.setId("DUMMY_ID");
        when(currentUserService.tryToGetUserFromToken((Try<String>) any())).thenReturn(Try.success(currentUser));

        User user = new User();
        user.setId("DUMMY_ID");
        when(userService.tryToGetUserById(anyString())).thenReturn(Try.success(user));

        when(messageRepository.countByConversationId(anyString())).thenReturn(50L);
        when(messageRepository.countByRecipientIdAndIsReadAndConversationId(anyString(), anyBoolean(), anyString())).thenReturn(5L);

        InitialMessagePagesPayload initialMessagePagesPayload = messageService.getLatestPagesToLoad(DUMMY_TOKEN, "DUMMY_ID");
        Assertions.assertEquals(1, initialMessagePagesPayload.getPagesToLoad().size());
        Assertions.assertEquals(2, initialMessagePagesPayload.getPagesToLoad().get(0));
    }

    @Test
    @DisplayName("It tests getting the latest pages when there are many unread messages")
    protected void getLatestPagesToLoadWhenThereAreManyUnreadMessages() {
        messageService.setNumberOfMessagesPerPage(20);

        User currentUser = new User();
        currentUser.setId("DUMMY_ID");
        when(currentUserService.tryToGetUserFromToken((Try<String>) any())).thenReturn(Try.success(currentUser));

        User user = new User();
        user.setId("DUMMY_ID");
        when(userService.tryToGetUserById(anyString())).thenReturn(Try.success(user));

        when(messageRepository.countByConversationId(anyString())).thenReturn(110L);
        when(messageRepository.countByRecipientIdAndIsReadAndConversationId(anyString(), anyBoolean(), anyString())).thenReturn(50L);

        InitialMessagePagesPayload initialMessagePagesPayload = messageService.getLatestPagesToLoad(DUMMY_TOKEN, "DUMMY_ID");
        Assertions.assertEquals(3, initialMessagePagesPayload.getPagesToLoad().size());
        Assertions.assertTrue(initialMessagePagesPayload.getPagesToLoad().contains(5));
        Assertions.assertTrue(initialMessagePagesPayload.getPagesToLoad().contains(4));
        Assertions.assertTrue(initialMessagePagesPayload.getPagesToLoad().contains(3));
    }

}
