package net.bean.java.open.messenger.service.implementation;

import lombok.RequiredArgsConstructor;
import net.bean.java.open.messenger.repository.MessageMongoDbRepository;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public abstract class MessageServiceV2ImplExt {

    protected int numberOfMessagesPerPage;

    final MessageMongoDbRepository messageRepository;

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

    final long getNumberOfUnreadMessagesForUser(String conversationId, long userId) {
        return messageRepository.countByRecipientIdAndIsReadAndConversationId(userId,false, conversationId);
    }

    final List<Long> getNumberOfPagesForUnreadMessages(long numberOfPages, long numberOfPagesForUnreadMessages ) {
        List<Long> pages = new ArrayList<>();
        for(long i = numberOfPages - numberOfPagesForUnreadMessages; i < numberOfPages ; i++) {
            pages.add(i);
        }
        return pages;
    }

    @Value("${application.message.page}")
    public void setNumberOfMessagesPerPage(String numberOfMessagesPerPage) {
        this.numberOfMessagesPerPage = Integer.parseInt(numberOfMessagesPerPage);
    }

    public void setNumberOfMessagesPerPage(int numberOfMessagesPerPage) {
        this.numberOfMessagesPerPage = numberOfMessagesPerPage;
    }

}
