package net.bean.java.open.messenger.repository;

import net.bean.java.open.messenger.model.Message;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MessageRepository {

    Long countByConversationId(String conversationId);

    Long countByRecipientIdAndIsReadAndConversationId(String recipientId, boolean isAcknowledged, String conversationId);

    List<Message> findByConversationId(String conversationId, Pageable pageable);

    Message save(Message message);

    void deleteAll();

}
