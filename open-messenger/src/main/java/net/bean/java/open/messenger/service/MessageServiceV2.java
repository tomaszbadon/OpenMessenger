package net.bean.java.open.messenger.service;

import net.bean.java.open.messenger.rest.model.InputMessagePayload;
import net.bean.java.open.messenger.rest.model.OutputMessagePayload;

public interface MessageServiceV2 {

    OutputMessagePayload handleNewMessage(InputMessagePayload inputMessagePayload, String token);

}
