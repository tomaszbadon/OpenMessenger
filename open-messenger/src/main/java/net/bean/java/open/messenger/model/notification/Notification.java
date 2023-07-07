package net.bean.java.open.messenger.model.notification;

import java.io.Serializable;
import java.util.List;

public record Notification(List<NotificationMessage> unreadNotificationMessages) implements Serializable { }
