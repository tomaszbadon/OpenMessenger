package net.bean.java.open.messenger.service.implementation;

import net.bean.java.open.messenger.model.entity.User;
import net.bean.java.open.messenger.model.entity.mongo.Message;
import net.bean.java.open.messenger.repository.MessageMongoDbRepository;
import net.bean.java.open.messenger.rest.model.InitialMessagePagesPayload;
import net.bean.java.open.messenger.rest.model.InputMessagePayload;
import net.bean.java.open.messenger.rest.model.OutputMessagePayload;
import net.bean.java.open.messenger.service.CurrentUserService;
import net.bean.java.open.messenger.service.MessageServiceV2;
import net.bean.java.open.messenger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
@Service
public class MessageServiceV2Impl extends MessageServiceV2ImplExt implements MessageServiceV2 {

    private final UserService userService;

    private final CurrentUserService currentUserService;

    @Autowired
    public MessageServiceV2Impl(MessageMongoDbRepository messageRepository, UserService userService, CurrentUserService currentUserService) {
        super(messageRepository);
        this.userService = userService;
        this.currentUserService = currentUserService;
    }

    @Override
    public OutputMessagePayload handleNewMessage(InputMessagePayload inputMessagePayload, String token) {
        User sender = currentUserService.getUserFromTokenOrElseThrowException(token);
        User recipient = userService.getUserOrElseThrowException(inputMessagePayload.getRecipient());
        Message message = Message.of(sender.getId(), recipient.getId(), inputMessagePayload.getMessage());
        return new OutputMessagePayload(messageRepository.save(message));
    }

    @Override
    public OutputMessagePayload handleNewMessage(InputMessagePayload inputMessagePayload, Date sendAt, User sender) {
        User recipient = userService.getUserOrElseThrowException(inputMessagePayload.getRecipient());
        Message message = Message.of(sender.getId(), recipient.getId(), inputMessagePayload.getMessage(), sendAt);
        return new OutputMessagePayload(messageRepository.save(message));
    }

    @Override
    public OutputMessagePayload handleNewMessage(InputMessagePayload inputMessagePayload, Date sendAt, User sender, boolean isRead) {
        User recipient = userService.getUserOrElseThrowException(inputMessagePayload.getRecipient());
        Message message = Message.of(sender.getId(), recipient.getId(), inputMessagePayload.getMessage(), sendAt, isRead);
        return new OutputMessagePayload(messageRepository.save(message));
    }

    public InitialMessagePagesPayload getLatestPagesToLoad(String token, long userId) {
        User currentUser = currentUserService.getUserFromTokenOrElseThrowException(token);
        User user = userService.getUserOrElseThrowException(userId);
        String conversationId = Message.conversationId(currentUser.getId(), user.getId());
        long numberOfPages = getNumberOfPagesByConversationId(conversationId);
        if(numberOfPages == 0) {
            return new InitialMessagePagesPayload(List.of());
        } else {
            long unreadMessages = getNumberOfUnreadMessagesForUser(conversationId, currentUser.getId());
            if (unreadMessages == 0) {
                return new InitialMessagePagesPayload(List.of(numberOfPages - 1));
            } else {
                long numberOfPagesForUnreadMessages = getNumberOfPages(unreadMessages);
                return new InitialMessagePagesPayload(getNumberOfPagesForUnreadMessages(numberOfPages, numberOfPagesForUnreadMessages));
            }
        }
    }


}
