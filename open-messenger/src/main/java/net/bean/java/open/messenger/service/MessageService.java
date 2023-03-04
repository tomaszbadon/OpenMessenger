package net.bean.java.open.messenger.service;

import io.vavr.control.Try;
import net.bean.java.open.messenger.model.entity.User;
import net.bean.java.open.messenger.rest.model.InitialMessagePagesPayload;
import net.bean.java.open.messenger.rest.model.InputMessagePayload;
import net.bean.java.open.messenger.rest.model.OutputMessagePayload;
import net.bean.java.open.messenger.rest.model.OutputMessagesPayload;

import java.util.Date;
import java.util.Optional;

public interface MessageService {

    OutputMessagePayload handleNewMessage(InputMessagePayload inputMessagePayload, Try<String> token);

    OutputMessagePayload handleNewMessage(InputMessagePayload inputMessagePayload, Date sendAt, User sender);

    OutputMessagePayload handleNewMessage(InputMessagePayload inputMessagePayload, Date sendAt, User sender, boolean isRead);

    InitialMessagePagesPayload getLatestPagesToLoad(Try<String>  token, long userId);

    OutputMessagesPayload readMessages(Try<String>  token, long userId, Optional<Integer> page);

}
