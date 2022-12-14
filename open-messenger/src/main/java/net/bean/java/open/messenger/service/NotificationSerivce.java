package net.bean.java.open.messenger.service;

import net.bean.java.open.messenger.rest.model.Notification;
import net.bean.java.open.messenger.model.entity.Message;

import java.util.List;

public interface NotificationSerivce {

    void notifyUser(Message message);

    List<Notification> getNotification(long userId);

}
