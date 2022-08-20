package net.bean.java.open.messenger.repository;

import net.bean.java.open.messenger.data.jpa.model.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MessageRepository extends PagingAndSortingRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE (m.recipient.id = ?1 AND m.sender.id = ?2) OR (m.sender.id = ?1 AND m.recipient.id = ?2)")
    List<Message> getConversationBetweenUsers(long user1, long user2, Pageable pageable);

}
