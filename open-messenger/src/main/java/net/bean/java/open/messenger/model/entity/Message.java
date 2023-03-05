package net.bean.java.open.messenger.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
@NoArgsConstructor
public class Message {

    public final static String ID = "id";
    @Id
    private String id;
    private String conversationId;
    private String senderId;
    private String recipientId;
    private String message;
    private boolean isRead;
    private Date sentAt;

    public static Message of(String senderId, String recipientId, String messageContent) {
        Message messages = new Message();
        messages.conversationId = conversationId(recipientId, senderId);
        messages.recipientId = recipientId;
        messages.senderId = senderId;
        messages.message = messageContent;
        messages.isRead = false;
        messages.sentAt = new Date();
        return messages;
    }

    public static Message of(String senderId, String recipientId, String messageContent, Date sentAt) {
        Message messages = new Message();
        messages.conversationId = conversationId(recipientId, senderId);
        messages.recipientId = recipientId;
        messages.senderId = senderId;
        messages.message = messageContent;
        messages.isRead = false;
        messages.sentAt = sentAt;
        return messages;
    }

    public static Message of(String senderId, String recipientId, String messageContent, Date sentAt, boolean isRead) {
        Message messages = new Message();
        messages.conversationId = conversationId(recipientId, senderId);
        messages.recipientId = recipientId;
        messages.senderId = senderId;
        messages.message = messageContent;
        messages.isRead = isRead;
        messages.sentAt = sentAt;
        return messages;
    }

    public static String conversationId(String recipientId, String senderId) {
        if(recipientId.compareTo(senderId) < 0) {
            return recipientId + "_" + senderId;
        } else {
            return senderId + "_" + recipientId;
        }
    }

}