package net.bean.java.open.messenger.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bean.java.open.messenger.rest.model.InputMessagePayload;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String content;
    private boolean isAcknowledged;

    @ManyToOne(fetch = FetchType.EAGER)
    private User sender;

    @ManyToOne(fetch = FetchType.EAGER)
    private User recipient;

    @Temporal(TemporalType.TIMESTAMP)
    private Date sentAt;

    public Message(InputMessagePayload inputMessagePayload, User sender, User recipient, Date sentAt, boolean isAcknowledged) {
        this.content = inputMessagePayload.getMessage();
        this.isAcknowledged = isAcknowledged;
        this.sender = sender;
        this.recipient = recipient;
        this.sentAt = sentAt;
    }

}
