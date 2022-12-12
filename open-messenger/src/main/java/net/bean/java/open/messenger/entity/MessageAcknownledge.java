package net.bean.java.open.messenger.entity;

import lombok.Data;

import java.util.Collection;

@Data
public class MessageAcknownledge {

    private Collection<Notification> notificationsToAcknowledge;

}
