package net.bean.java.open.messenger.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UnreadMessage {

    private String sender;
    private String messageId;

}