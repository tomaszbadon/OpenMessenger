package net.bean.java.open.messenger.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.bean.java.open.messenger.model.jpa.Message;

import java.text.SimpleDateFormat;

@Data
@NoArgsConstructor
public class OutputMessagePayload {

    private static SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    private long id;
    private String message;
    private Long recipient;
    private Long sender;
    private boolean isAcknowledged;
    private String sentAt;

    public OutputMessagePayload(Message m) {
        id = m.getId();
        message = m.getContent();
        recipient = m.getRecipient().getId();
        sender = m.getSender().getId();
        isAcknowledged = m.isAcknowledged();
        sentAt = format.format(m.getSentAt());
    }

}
