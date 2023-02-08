package net.bean.java.open.messenger.service.implementation;

import net.bean.java.open.messenger.model.entity.User;
import net.bean.java.open.messenger.model.entity.mongo.Message;
import net.bean.java.open.messenger.repository.MessageMongoDbRepository;
import net.bean.java.open.messenger.rest.model.InitPagesPayload;
import net.bean.java.open.messenger.rest.model.InputMessagePayload;
import net.bean.java.open.messenger.rest.model.OutputMessagePayload;
import net.bean.java.open.messenger.service.CurrentUserService;
import net.bean.java.open.messenger.service.MessageServiceV2;
import net.bean.java.open.messenger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class MessageServiceV2Impl extends MessageServiceV2ImplMixin implements MessageServiceV2 {

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
    public OutputMessagePayload handleNewMessage(InputMessagePayload inputMessagePayload, Date sendAt, User sender, User recipient) {
        Message message = Message.of(sender.getId(), recipient.getId(), inputMessagePayload.getMessage());
        message.setSentAt(sendAt);
        return new OutputMessagePayload(messageRepository.save(message));
    }

    public InitPagesPayload getLatestPagesToLoad(String token, long userId) {
        User currentuser = currentUserService.getUserFromTokenOrElseThrowException(token);
        User user = userService.getUserOrElseThrowException(userId);
        String conversationId = Message.conversationId(currentuser.getId(), user.getId());
        long numberOfPages = getNumberOfPagesByConversationId(conversationId);
        long unreadMessages = getNumberOfUnreadMessages(conversationId);

        if (unreadMessages == 0) {
            return new InitPagesPayload(List.of(numberOfPages - 1));
        } else {
            long numberOfPagesForUnreadMessages = getNumberOfPages(unreadMessages);
            List<Long> pages = new ArrayList<>();
            for(long i = numberOfPages - numberOfPagesForUnreadMessages; i < numberOfPages ; i++) {
                pages.add(i);
            }
            return new InitPagesPayload(pages);
        }
    }
}
