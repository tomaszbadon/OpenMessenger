package net.bean.java.open.messenger.service.implementation;

import lombok.RequiredArgsConstructor;
import net.bean.java.open.messenger.rest.model.InputMessagePayload;
import net.bean.java.open.messenger.rest.model.OutputMessagePayload;
import net.bean.java.open.messenger.repository.MessageRepository;
import net.bean.java.open.messenger.service.MessageServiceV2;
import net.bean.java.open.messenger.service.UserService;
import net.bean.java.open.messenger.service.validator.MessageServiceValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class MessageServiceV2Impl implements MessageServiceV2 {

    private final JwtTokenServiceImpl jwtTokenService;

    private final UserService userService;

    private final MessageRepository messageRepository;

    private final MessageServiceValidator validator;

    @Override
    public OutputMessagePayload handleNewMessage(InputMessagePayload inputMessagePayload, String token, long senderId, long recipientId) {



        return null;

    }



}
