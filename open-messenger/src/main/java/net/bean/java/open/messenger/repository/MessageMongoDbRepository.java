package net.bean.java.open.messenger.repository;

import net.bean.java.open.messenger.model.entity.mongo.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageMongoDbRepository extends MongoRepository<Message, String> {

    Long countByConversationId(String conversationId);

    Long countByRecipientIdAndIsReadAndConversationId(long recipientId, boolean isAcknowledged, String conversationId);

}
