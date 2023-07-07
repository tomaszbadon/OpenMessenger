package net.bean.java.open.messenger.model.notification;

import java.io.Serializable;

public record NotificationMessage(String messageId, String conversationId) implements Serializable { }
