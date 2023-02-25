package net.bean.java.open.messenger.rest.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;

@Data
@NoArgsConstructor
public class OutputMessagePayload {

    private static SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    private String id;
    private String message;
    private Long recipient;
    private Long sender;
    private boolean isRead;
    private String sentAt;

    @Deprecated
    public OutputMessagePayload(net.bean.java.open.messenger.model.entity.Message m) {
        id = String.valueOf(m.getId());
        message = m.getContent();
        recipient = m.getRecipient().getId();
        sender = m.getSender().getId();
        isRead = m.isAcknowledged();
        sentAt = format.format(m.getSentAt());
    }

    public OutputMessagePayload(net.bean.java.open.messenger.model.entity.mongo.Message m) {
        id = m.getId();
        message = m.getMessage();
        recipient = m.getRecipientId();
        sender = m.getSenderId();
        isRead = m.isRead();
        sentAt = format.format(m.getSentAt());
    }

}
