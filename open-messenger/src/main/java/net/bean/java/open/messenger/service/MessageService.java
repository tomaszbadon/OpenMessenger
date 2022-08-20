package net.bean.java.open.messenger.service;

import net.bean.java.open.messenger.data.dto.MessageDTO;
import net.bean.java.open.messenger.data.jpa.model.Message;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Repository
public interface MessageService {

    Message saveMessage(MessageDTO message);

    Message saveMessageWithSpecificDate(MessageDTO message) throws ParseException;

    List<Message> getConversationBetweenUsers(long userId1, long userId2, Optional<Integer> page);

}
