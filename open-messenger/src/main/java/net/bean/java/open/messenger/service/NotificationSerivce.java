package net.bean.java.open.messenger.service;

import net.bean.java.open.messenger.data.dto.Notification;
import net.bean.java.open.messenger.data.jpa.model.Message;

import java.util.List;

public interface NotificationSerivce {

    void notifyUser(Message message);

    List<Notification> getNotification(long userId);

}
