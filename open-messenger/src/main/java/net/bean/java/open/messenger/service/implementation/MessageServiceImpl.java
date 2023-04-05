package net.bean.java.open.messenger.service.implementation;

import io.vavr.control.Try;
import net.bean.java.open.messenger.model.User;
import net.bean.java.open.messenger.model.Message;
import net.bean.java.open.messenger.repository.MessageRepository;
import net.bean.java.open.messenger.rest.model.InitialMessagePagesPayload;
import net.bean.java.open.messenger.rest.model.InputMessagePayload;
import net.bean.java.open.messenger.rest.model.OutputMessagePayload;
import net.bean.java.open.messenger.rest.model.OutputMessagesPayload;
import net.bean.java.open.messenger.service.CurrentUserService;
import net.bean.java.open.messenger.service.MessageService;
import net.bean.java.open.messenger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class MessageServiceImpl extends MessageServiceV2ImplExt implements MessageService {

    private final UserService userService;

    private final CurrentUserService currentUserService;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, UserService userService, CurrentUserService currentUserService) {
        super(messageRepository);
        this.userService = userService;
        this.currentUserService = currentUserService;
    }

    @Override
    public OutputMessagePayload handleNewMessage(InputMessagePayload inputMessagePayload, Try<String> token) {
        User sender = currentUserService.tryToGetUserFromToken(token).get();
        User recipient = userService.tryToGetUser(inputMessagePayload.getRecipient()).get();
        Message message = Message.of(sender.getId(), recipient.getId(), inputMessagePayload.getMessage());
        return new OutputMessagePayload(messageRepository.save(message));
    }

    @Override
    public OutputMessagePayload handleNewMessage(InputMessagePayload inputMessagePayload, Date sendAt, User sender) {
        User recipient = userService.tryToGetUser(inputMessagePayload.getRecipient()).get();
        Message message = Message.of(sender.getId(), recipient.getId(), inputMessagePayload.getMessage(), sendAt);
        return new OutputMessagePayload(messageRepository.save(message));
    }

    @Override
    public OutputMessagePayload handleNewMessage(InputMessagePayload inputMessagePayload, Date sendAt, User sender, boolean isRead) {
        User recipient = userService.tryToGetUser(inputMessagePayload.getRecipient()).get();
        Message message = Message.of(sender.getId(), recipient.getId(), inputMessagePayload.getMessage(), sendAt, isRead);
        return new OutputMessagePayload(messageRepository.save(message));
    }

    public InitialMessagePagesPayload getLatestPagesToLoad(Try<String> token, String userId) {
        User currentUser = currentUserService.tryToGetUserFromToken(token).get();
        User user = userService.tryToGetUser(userId).get();
        String conversationId = Message.conversationId(currentUser.getId(), user.getId());
        long numberOfPages = getNumberOfPagesByConversationId(conversationId);
        if(numberOfPages == 0) {
            return new InitialMessagePagesPayload(List.of());
        } else {
            long unreadMessages = getNumberOfUnreadMessagesForUser(conversationId, currentUser.getId());
            if (unreadMessages == 0) {
                return new InitialMessagePagesPayload(List.of((int) numberOfPages - 1));
            } else {
                long numberOfPagesForUnreadMessages = getNumberOfPages(unreadMessages);
                return new InitialMessagePagesPayload(getNumberOfPagesForUnreadMessages(numberOfPages, numberOfPagesForUnreadMessages));
            }
        }
    }

    @Override
    public OutputMessagesPayload readMessages(Try<String> token, String userId, Optional<Integer> pageOptional) {
        User currentUser = currentUserService.tryToGetUserFromToken(token).get();
        User user = userService.tryToGetUser(userId).get();
        String conversationId = Message.conversationId(currentUser.getId(), user.getId());
        Sort sort = Sort.by(Message.ID).ascending();
        int page = pageOptional.orElse(0);
        Pageable pageable = PageRequest.of(page, numberOfMessagesPerPage, sort);
        List<Message> messages = messageRepository.findByConversationId(conversationId, pageable);
        return new OutputMessagesPayload(messages.stream().map(OutputMessagePayload::new).collect(Collectors.toList()), page);
    }
}
