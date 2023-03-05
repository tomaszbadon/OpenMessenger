package net.bean.java.open.messenger.repository;

import net.bean.java.open.messenger.model.entity.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {

    Long countByConversationId(String conversationId);

    Long countByRecipientIdAndIsReadAndConversationId(String recipientId, boolean isAcknowledged, String conversationId);

    List<Message> findByConversationId(String conversationId, Pageable pageable);

}
