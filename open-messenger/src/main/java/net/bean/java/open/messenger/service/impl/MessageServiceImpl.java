package net.bean.java.open.messenger.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bean.java.open.messenger.entity.InputMessagePayload;
import net.bean.java.open.messenger.entity.Notification;
import net.bean.java.open.messenger.model.jpa.Message;
import net.bean.java.open.messenger.repository.MessageRepository;
import net.bean.java.open.messenger.service.MessageService;
import net.bean.java.open.messenger.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MessageServiceImpl implements MessageService {

    private final static int DEFAULT_PAGE_SIZE = 20;

    private final UserService userService;

    public final MessageRepository messageRepository;

    @Override
    public Message saveMessage(InputMessagePayload messageDto, long senderId) {
        Message message = new Message();
        message.setContent(messageDto.getMessage());
        message.setSentAt(new Date());
        message.setAcknowledged(false);
        userService.getUser(messageDto.getRecipient()).ifPresent((recipient) -> message.setRecipient(recipient));
        userService.getUser(senderId).ifPresent((sender) -> message.setSender(sender));
        Message savedMessage = messageRepository.save(message);
        return savedMessage;
    }

    @Override
    public Message saveMessageWithSpecificDate(InputMessagePayload messageDto, long senderId, String sentAt) throws ParseException {
        Message message = saveMessage(messageDto, senderId);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        message.setSentAt(format.parse(sentAt));
        messageRepository.save(message);
        return message;
    }

    @Override
    public List<Message> getMessages(long userId1, long userId2, Optional<Integer> page) {
        Sort sort = Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(page.orElse(0), DEFAULT_PAGE_SIZE, sort);
        return messageRepository.getMessages(userId1, userId2, pageable);
    }

    @Override
    public int getLastPage(long userId1, long userId2) {
        long totalNumberOfMessages = messageRepository.getNumberOfMessages(userId1, userId2);
        int page = (int) totalNumberOfMessages / DEFAULT_PAGE_SIZE;
        if(page > 0 && totalNumberOfMessages % DEFAULT_PAGE_SIZE == 0) {
            page--;
        }
        return page;
    }

    @Override
    public Message getMessageById(long id) {
        return messageRepository.getMessageById(id);
    }

    @Override
    public List<Message> getUnacknowledgedMessages(long userId) {
        return messageRepository.getUnacknownledgedMessages(userId);
    }

    @Override
    public void acknowledgedMessages(long userId, Collection<Notification> messages) {
        List<Long> messageIds = messages.stream().map(m -> m.getMessageId()).collect(Collectors.toList());
        messageRepository.acknownledgedMessages(userId, messageIds);
    }
}