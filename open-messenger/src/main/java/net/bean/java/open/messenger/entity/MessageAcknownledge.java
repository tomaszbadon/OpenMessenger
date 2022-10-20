package net.bean.java.open.messenger.entity;

import lombok.Data;
import net.bean.java.open.messenger.data.dto.Notification;

import java.util.Collection;

@Data
public class MessageAcknownledge {

    private Collection<Notification> notificationsToAcknowledge;

}
