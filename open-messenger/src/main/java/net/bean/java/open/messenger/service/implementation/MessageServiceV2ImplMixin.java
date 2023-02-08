package net.bean.java.open.messenger.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.bean.java.open.messenger.repository.MessageMongoDbRepository;
import org.springframework.beans.factory.annotation.Value;

@RequiredArgsConstructor
public abstract class MessageServiceV2ImplMixin {

    @Setter
    @Value("${application.message.page}")
    private String numberOfMessagesPerPage;

    protected final MessageMongoDbRepository messageRepository;

    final long getNumberOfMessagesPerPage() {
        return Long.parseLong(numberOfMessagesPerPage);
    }

    final long getNumberOfPagesByConversationId(String conversationId) {
        return getNumberOfPages(messageRepository.countByConversationId(conversationId));
    }

    final long getNumberOfPages(long numberOfMessages) {
        long numberOfMessagesPerPage = getNumberOfMessagesPerPage();
        long numberOfPages = numberOfMessages / numberOfMessagesPerPage;
        if(numberOfMessages % numberOfMessagesPerPage == 0) {
            return numberOfPages;
        } else {
            return ++numberOfPages;
        }
    }

    final long getNumberOfUnreadMessages(String conversationId) {
        return messageRepository.countByIsReadAndConversationId(false, conversationId);
    }


}
