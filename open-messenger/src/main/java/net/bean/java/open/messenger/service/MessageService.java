package net.bean.java.open.messenger.service;

import net.bean.java.open.messenger.entity.InputMessagePayload;
import net.bean.java.open.messenger.entity.Notification;
import net.bean.java.open.messenger.model.jpa.Message;

import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MessageService {

    Message saveMessage(InputMessagePayload message, long senderId);

    Message saveMessageWithSpecificDate(InputMessagePayload message, long senderId, String sentAt) throws ParseException;

    List<Message> getMessages(long userId1, long userId2, Optional<Integer> page);

    int getLastPage(long user1, long user2);

    Message getMessageById(long id);

    List<Message> getUnacknowledgedMessages(long userId);

    void acknowledgedMessages(long userId, Collection<Notification> messages);

}
