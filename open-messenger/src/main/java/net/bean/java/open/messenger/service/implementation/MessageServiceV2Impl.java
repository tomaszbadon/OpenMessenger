package net.bean.java.open.messenger.service.implementation;

import lombok.RequiredArgsConstructor;
import net.bean.java.open.messenger.model.entity.User;
import net.bean.java.open.messenger.model.entity.mongo.Message;
import net.bean.java.open.messenger.repository.MessageMongoDbRepository;
import net.bean.java.open.messenger.rest.model.InputMessagePayload;
import net.bean.java.open.messenger.rest.model.OutputMessagePayload;
import net.bean.java.open.messenger.service.CurrentUserService;
import net.bean.java.open.messenger.service.MessageServiceV2;
import net.bean.java.open.messenger.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional
@Service
@RequiredArgsConstructor
public class MessageServiceV2Impl implements MessageServiceV2 {

    private final JwtTokenServiceImpl jwtTokenService;

    private final UserService userService;

    private final CurrentUserService currentUserService;

    private final MessageMongoDbRepository messageRepository;

    @Override
    public OutputMessagePayload handleNewMessage(InputMessagePayload inputMessagePayload, String token) {
        User sender = currentUserService.getUserFromTokenOrElseThrowException(token);
        User recipient = userService.getUserOrElseThrowException(inputMessagePayload.getRecipient());
        Message message = Message.of(sender.getId(), recipient.getId(), inputMessagePayload.getMessage());
        return new OutputMessagePayload(messageRepository.save(message));
    }

    @Override
    public OutputMessagePayload handleNewMessage(InputMessagePayload inputMessagePayload, Date sendAt, User sender, User recipient) {
        Message message = Message.of(sender.getId(), recipient.getId(), inputMessagePayload.getMessage());
        message.setSentAt(sendAt);
        return new OutputMessagePayload(messageRepository.save(message));
    }
}
