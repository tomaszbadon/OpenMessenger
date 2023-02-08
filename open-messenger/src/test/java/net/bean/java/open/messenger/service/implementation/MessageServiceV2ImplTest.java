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
    @DisplayName("Tests Getting the latest Pages When All Messages Are Read")
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
    @DisplayName("Tests Getting the latest Pages When there a few unread messages ")
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
        //Assertions.assertEquals(1, initPagesPayload.getPagesToLoad().size());
        //Assertions.assertEquals(0, initPagesPayload.getPagesToLoad().get(0));
    }

}
