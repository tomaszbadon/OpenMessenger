package net.bean.java.open.messenger.model.entity.mongo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
@NoArgsConstructor
public class Message {
    @Id
    private String id;
    private String conversationId;
    private long senderId;
    private long recipientId;
    private String message;
    private boolean isRead;
    private Date sentAt;

    public static Message of(long senderId, long recipientId, String messageContent) {
        Message messages = new Message();
        messages.conversationId = conversationId(recipientId, senderId);
        messages.recipientId = recipientId;
        messages.senderId = senderId;
        messages.message = messageContent;
        messages.isRead = false;
        messages.sentAt = new Date();
        return messages;
    }

    public static String conversationId(long recipientId, long senderId) {
        if(recipientId < senderId) {
            return recipientId + "_" + senderId;
        } else {
            return senderId + "_" + recipientId;
        }
    }

}