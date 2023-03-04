package net.bean.java.open.messenger.repository;

import net.bean.java.open.messenger.model.entity.mongo.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {

    Long countByConversationId(String conversationId);

    Long countByRecipientIdAndIsReadAndConversationId(long recipientId, boolean isAcknowledged, String conversationId);

    List<Message> findByConversationId(String conversationId, Pageable pageable);

}
