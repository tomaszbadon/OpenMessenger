package net.bean.java.open.messenger.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Notification {

    private long sender;
    private long messageId;

}