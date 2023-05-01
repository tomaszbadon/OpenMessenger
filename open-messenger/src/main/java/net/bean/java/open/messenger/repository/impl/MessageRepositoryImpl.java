package net.bean.java.open.messenger.repository.impl;

import lombok.RequiredArgsConstructor;
import net.bean.java.open.messenger.model.Message;
import net.bean.java.open.messenger.repository.MessageRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Repository
public class MessageRepositoryImpl implements MessageRepository {

    private final MongoTemplate template;

    @Override
    public Long countByConversationId(String conversationId) {
        Query query = new Query();
        query.addCriteria(Criteria.where(Message.CONVERSATION_ID).is(conversationId));
        return template.count(query, Message.class);
    }

    @Override
    public Long countByRecipientIdAndIsReadAndConversationId(String recipientId, boolean isRead, String conversationId) {
        Query query = new Query();
        query.addCriteria(Criteria.where(Message.RECIPIENT_ID).is(recipientId)
                                  .and(Message.IS_READ).is(isRead)
                                  .and(Message.CONVERSATION_ID).is(conversationId));
        return template.count(query, Message.class);
    }

    @Override
    public List<Message> findByConversationId(String conversationId, Pageable pageable) {
        Query query = new Query();
        query.addCriteria(Criteria.where(Message.CONVERSATION_ID).is(conversationId));
        query.with(pageable);
        return template.find(query, Message.class);
    }

    @Override
    public Optional<Message> findById(String messageId) {
        return Optional.ofNullable(template.findById(messageId, Message.class));
    }

    @Override
    public Message save(Message message) {
        return template.save(message);
    }

    @Override
    public void deleteAll() {
        template.remove(new Query(), Message.class);
    }
}
