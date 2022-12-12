package net.bean.java.open.messenger.service;

import net.bean.java.open.messenger.entity.Notification;
import net.bean.java.open.messenger.model.jpa.Message;

import java.util.List;

public interface NotificationSerivce {

    void notifyUser(Message message);

    List<Notification> getNotification(long userId);

}
