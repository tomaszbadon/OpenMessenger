package net.bean.java.open.messenger.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bean.java.open.messenger.data.dto.InputMessageDTO;
import net.bean.java.open.messenger.data.jpa.model.Message;
import net.bean.java.open.messenger.repository.MessageRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MessageServiceImpl implements MessageService {

    private final static int DEFAULT_PAGE_SIZE = 20;

    private final UserService userService;

    public final MessageRepository messageRepository;

    @Override
    public Message saveMessage(InputMessageDTO messageDto, long senderId) {
        Message message = new Message();
        message.setContent(messageDto.getMessage());
        message.setSentAt(new Date());
        message.setAcknowledged(false);
        userService.getUser(messageDto.getRecipient()).ifPresent((recipient) -> message.setRecipient(recipient));
        userService.getUser(senderId).ifPresent((sender) -> message.setSender(sender));
        Message savedMessage = messageRepository.save(message);
        log.debug("Message between: {} and {} was created", savedMessage.getRecipient().getId(), savedMessage.getSender().getId());
        return savedMessage;
    }

    @Override
    public Message saveMessageWithSpecificDate(InputMessageDTO messageDto, long senderId, String sentAt) throws ParseException {
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

}