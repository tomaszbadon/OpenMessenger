package net.bean.java.open.messenger.service;

import net.bean.java.open.messenger.model.Message;
import net.bean.java.open.messenger.rest.model.Notification;

import java.util.List;

public interface NotificationSerivce {

    void notifyUser(Message message);

    List<Notification> getNotification(long userId);

}
