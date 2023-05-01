package net.bean.java.open.messenger.repository;

import net.bean.java.open.messenger.model.Message;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MessageRepository {

    Long countByConversationId(String conversationId);

    Long countByRecipientIdAndIsReadAndConversationId(String recipientId, boolean isAcknowledged, String conversationId);

    List<Message> findByConversationId(String conversationId, Pageable pageable);

    Optional<Message> findById(String messageId);

    Message save(Message message);

    void deleteAll();

}
