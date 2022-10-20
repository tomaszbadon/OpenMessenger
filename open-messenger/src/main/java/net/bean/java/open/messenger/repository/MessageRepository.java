package net.bean.java.open.messenger.repository;

import net.bean.java.open.messenger.data.jpa.model.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;
import java.util.List;

public interface MessageRepository extends PagingAndSortingRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE (m.recipient.id = ?1 AND m.sender.id = ?2) OR (m.sender.id = ?1 AND m.recipient.id = ?2)")
    List<Message> getMessages(long user1, long user2, Pageable pageable);

    @Query("SELECT m FROM Message m WHERE ((m.recipient.id = ?1 AND m.sender.id = ?2) OR (m.sender.id = ?1 AND m.recipient.id = ?2)) AND m.id < ?3")
    List<Message> getMessagesWithLowerIdThan(long user1, long user2, Pageable pageable, long lowerIdThan);

    @Query("SELECT COUNT(m) FROM Message m WHERE (m.recipient.id = ?1 AND m.sender.id = ?2) OR (m.sender.id = ?1 AND m.recipient.id = ?2)")
    Long getNumberOfMessages(long user1, long user);

    Message getMessageById(long messageId);

    @Query("SELECT m FROM Message m WHERE m.isAcknowledged = false AND m.recipient.id = ?1")
    List<Message> getUnacknownledgedMessages(long userId);

    @Modifying
    @Query("UPDATE Message m SET m.isAcknowledged = true WHERE m.isAcknowledged = false AND m.recipient.id = ?1 AND m.id IN (?2)")
    void acknownledgedMessages(long userId, Collection<Long> messageIds);
}
