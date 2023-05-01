package net.bean.java.open.messenger.service.implementation;

import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import net.bean.java.open.messenger.model.Message;
import net.bean.java.open.messenger.model.User;
import net.bean.java.open.messenger.repository.MessageRepository;
import net.bean.java.open.messenger.rest.exception.MessageNotFoundException;
import net.bean.java.open.messenger.rest.exception.NoPermissionException;
import net.bean.java.open.messenger.rest.model.OutputMessagePayload;
import net.bean.java.open.messenger.service.CurrentUserService;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class MessageServiceV2ImplExt {

    protected int numberOfMessagesPerPage;

    final MessageRepository messageRepository;

    final CurrentUserService currentUserService;

    final long getNumberOfPagesByConversationId(String conversationId) {
        return getNumberOfPages(messageRepository.countByConversationId(conversationId));
    }

    final long getNumberOfPages(long numberOfMessages) {
        long numberOfPages = numberOfMessages / numberOfMessagesPerPage;
        if(numberOfMessages == 0) {
            return 0;
        } else if(numberOfMessages % numberOfMessagesPerPage == 0) {
            return numberOfPages;
        } else {
            return ++numberOfPages;
        }
    }

    final long getNumberOfUnreadMessagesForUser(String conversationId, String userId) {
        return messageRepository.countByRecipientIdAndIsReadAndConversationId(userId,false, conversationId);
    }

    final List<Integer> getNumberOfPagesForUnreadMessages(long numberOfPages, long numberOfPagesForUnreadMessages ) {
        List<Integer> pages = new ArrayList<>();
        for(long i = numberOfPages - numberOfPagesForUnreadMessages; i < numberOfPages ; i++) {
            pages.add((int) i);
        }
        return pages;
    }

    final Try<Message> tryToReadMessage(Try<String> token, String messageId) {
        Optional<Message> optional = messageRepository.findById(messageId);
        if(!optional.isPresent()) {
            return Try.failure(new MessageNotFoundException(messageId));
        }
        Message message = optional.get();
        return currentUserService.tryToGetUserFromToken(token).flatMap(user -> {
            if(isUserAllowedToFetch(message, user)) {
                return Try.success(message);
            } else {
                return Try.failure(new NoPermissionException());
            }
        });
    }

    @Value("${application.message.page}")
    public void setNumberOfMessagesPerPage(String numberOfMessagesPerPage) {
        this.numberOfMessagesPerPage = Integer.parseInt(numberOfMessagesPerPage);
    }

    public void setNumberOfMessagesPerPage(int numberOfMessagesPerPage) {
        this.numberOfMessagesPerPage = numberOfMessagesPerPage;
    }

    private boolean isUserAllowedToFetch(Message message, User user) {
        return message.getSenderId().equals(user.getId()) || message.getRecipientId().equals(user.getId());
    }

}
