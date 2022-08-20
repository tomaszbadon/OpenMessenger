package net.bean.java.open.messenger.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bean.java.open.messenger.data.dto.MessageDTO;
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
    public Message saveMessage(MessageDTO messageDto) {
        Message message = new Message();
        message.setContent(messageDto.getMessage());
        message.setSentAt(new Date());
        message.setAcknowledged(false);
        userService.getUser(messageDto.getRecipient()).ifPresent((recipient) -> message.setRecipient(recipient));
        userService.getUser(messageDto.getSender()).ifPresent((sender) -> message.setSender(sender));
        Message savedMessage = messageRepository.save(message);
        log.debug("Message between: {} and {} was created", savedMessage.getRecipient().getId(), savedMessage.getSender().getId());
        return savedMessage;
    }

    @Override
    public Message saveMessageWithSpecificDate(MessageDTO messageDto) throws ParseException {
        Message message = saveMessage(messageDto);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        message.setSentAt(format.parse(messageDto.getSentAt()));
        messageRepository.save(message);
        return message;
    }

    @Override
    public List<Message> getConversationBetweenUsers(long userId1, long userId2, Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.orElse(0), DEFAULT_PAGE_SIZE, Sort.by("id").ascending());
        return messageRepository.getConversationBetweenUsers(userId1, userId2, pageable);
    }

}