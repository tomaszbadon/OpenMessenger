package net.bean.java.open.messenger.service;

import net.bean.java.open.messenger.data.dto.InputMessageDTO;
import net.bean.java.open.messenger.data.jpa.model.Message;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Repository
public interface MessageService {

    Message saveMessage(InputMessageDTO message, long senderId);

    Message saveMessageWithSpecificDate(InputMessageDTO message, long senderId, String sentAt) throws ParseException;

    List<Message> getMessages(long userId1, long userId2, Optional<Integer> page);

    List<Message> getMessagesWithLowerIdThan(long userId1, long userId2, Optional<Integer> page, long lowerIdThan);

    Message getMessageById(long id);

}
