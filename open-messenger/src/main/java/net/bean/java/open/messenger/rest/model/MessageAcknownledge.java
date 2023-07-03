package net.bean.java.open.messenger.rest.model;

import lombok.Data;

import java.util.Collection;

@Data
public class MessageAcknownledge {

    private Collection<UnreadMessage> notificationsToAcknowledge;

}
