package net.bean.java.open.messenger.service;

import net.bean.java.open.messenger.model.entity.User;
import net.bean.java.open.messenger.rest.model.InitialMessagePagesPayload;
import net.bean.java.open.messenger.rest.model.InputMessagePayload;
import net.bean.java.open.messenger.rest.model.OutputMessagePayload;

import java.util.Date;

public interface MessageServiceV2 {

    OutputMessagePayload handleNewMessage(InputMessagePayload inputMessagePayload, String token);

    OutputMessagePayload handleNewMessage(InputMessagePayload inputMessagePayload, Date sendAt, User sender);

    OutputMessagePayload handleNewMessage(InputMessagePayload inputMessagePayload, Date sendAt, User sender, boolean isRead);

    InitialMessagePagesPayload getLatestPagesToLoad(String token, long userId);

}
