package net.bean.java.open.messenger.service.implementation;

import lombok.RequiredArgsConstructor;
import net.bean.java.open.messenger.model.entity.Message;
import net.bean.java.open.messenger.model.entity.User;
import net.bean.java.open.messenger.repository.MessageRepository;
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

    private final MessageRepository messageRepository;

    @Override
    public OutputMessagePayload handleNewMessage(InputMessagePayload inputMessagePayload, String token) {
        String senderUserName = jwtTokenService.getUserName(token);
        User sender = currentUserService.getUserFromTokenOrElseThrowException(token);
        User recipient = userService.getUserOrElseThrowException(inputMessagePayload.getRecipient());
        Message message = new Message(inputMessagePayload, sender, recipient, new Date(), false);
        return new OutputMessagePayload(messageRepository.save(message));
    }
}
