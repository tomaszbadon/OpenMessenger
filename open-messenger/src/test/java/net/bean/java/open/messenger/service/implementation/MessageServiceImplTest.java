package net.bean.java.open.messenger.service.implementation;

import io.vavr.control.Try;
import net.bean.java.open.messenger.exception.ExceptionConstants;
import net.bean.java.open.messenger.model.Message;
import net.bean.java.open.messenger.model.User;
import net.bean.java.open.messenger.repository.MessageRepository;
import net.bean.java.open.messenger.rest.exception.MessageNotFoundException;
import net.bean.java.open.messenger.exception.NoPermissionException;
import net.bean.java.open.messenger.rest.model.InitialMessagePagesPayload;
import net.bean.java.open.messenger.rest.model.OutputMessagePayload;
import net.bean.java.open.messenger.service.NotificationService;
import net.bean.java.open.messenger.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.text.MessageFormat;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MessageServiceImplTest {

    private final Try<String> DUMMY_TOKEN = Try.success("TOKEN");

    @Mock
    private UserService userService;

    @Mock
    private CurrentUserServiceImpl currentUserService;

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private MessageServiceImpl messageService;

    @Test
    @DisplayName("It tests getting the latest pages when there are no messages")
    void getLatestPagesToLoadWhenNoMessages() {
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
    void getLatestPagesToLoadWhenThereAreFewMessages() {
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
    void getLatestPagesToLoadWhenThereAreFewUnreadMessages() {
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
    void getLatestPagesToLoadWhenThereAreManyUnreadMessages() {
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

    @Test
    @DisplayName("It test getting a single message from a conversation")
    void getMessage() {
        User currentUser = new User();
        currentUser.setId("DUMMY_ID");
        when(currentUserService.tryToGetUserFromToken((Try<String>) any())).thenReturn(Try.success(currentUser));

        Message message = Message.of("DUMMY_ID", "DUMMY_ID_2", "This is a message");
        when(messageRepository.findById(any())).thenReturn(Optional.of(message));

        OutputMessagePayload outputMessagePayload = messageService.readMessage(DUMMY_TOKEN, "DUMMY_ID");
        Assertions.assertEquals(message.getMessage(), outputMessagePayload.getMessage());
        Assertions.assertEquals(message.getSenderId(), outputMessagePayload.getSender());
        Assertions.assertEquals(message.getRecipientId(), outputMessagePayload.getRecipient());

        verify(notificationService, times(1)).sendNotificationToUser(currentUser);
    }

    @Test
    @DisplayName("It test getting a 404 when a message does not exists")
    void getMessageWithMessageNotFound() {
        when(messageRepository.findById(any())).thenReturn(Optional.empty());

        MessageNotFoundException e = Assertions.assertThrows(MessageNotFoundException.class, () -> messageService.readMessage(DUMMY_TOKEN, "DUMMY_ID"));
        String expected = MessageFormat.format(ExceptionConstants.MESSAGE_NOT_FOUND, "DUMMY_ID");
        Assertions.assertTrue(e.getMessage().contains(expected));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
        verifyNoInteractions(notificationService);
    }

    @Test
    @DisplayName("It test getting a 403 when a message does not belong to client")
    void getMessageWhenNoPermission() {
        User currentUser = new User();
        currentUser.setId("DUMMY_ID_1");
        when(currentUserService.tryToGetUserFromToken((Try<String>) any())).thenReturn(Try.success(currentUser));

        Message message = Message.of("DUMMY_ID_2", "DUMMY_ID_3", "This is a message");
        when(messageRepository.findById(any())).thenReturn(Optional.of(message));

        NoPermissionException e = Assertions.assertThrows(NoPermissionException.class, () -> messageService.readMessage(DUMMY_TOKEN, "DUMMY_ID"));
        Assertions.assertTrue(e.getMessage().contains(ExceptionConstants.USER_DOES_NOT_HAVE_PERMISSION));
        Assertions.assertEquals(HttpStatus.FORBIDDEN, e.getStatus());
        verifyNoInteractions(notificationService);
    }

}
