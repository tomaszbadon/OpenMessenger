package net.bean.java.open.messenger.service;

import net.bean.java.open.messenger.model.Message;
import net.bean.java.open.messenger.model.User;

import java.util.List;

public interface NotificationService {

    void sendNotification(User user, List<Message> messages);

}
