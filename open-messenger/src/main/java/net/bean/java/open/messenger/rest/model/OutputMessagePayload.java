package net.bean.java.open.messenger.rest.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.bean.java.open.messenger.model.Message;

import java.text.SimpleDateFormat;

@Data
@NoArgsConstructor
public class OutputMessagePayload {

    private static SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    private String id;
    private String message;
    private String recipient;
    private String sender;
    private boolean isRead;
    private String sentAt;

    public OutputMessagePayload(Message m) {
        id = m.getId();
        message = m.getMessage();
        recipient = m.getRecipientId();
        sender = m.getSenderId();
        isRead = m.isRead();
        sentAt = format.format(m.getSentAt());
    }

}
