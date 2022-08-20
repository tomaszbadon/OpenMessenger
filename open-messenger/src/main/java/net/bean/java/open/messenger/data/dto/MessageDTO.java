package net.bean.java.open.messenger.data.dto;

import lombok.Data;

@Data
public class MessageDTO {

    private long id;
    private String message;
    private Long recipient;
    private Long sender;
    private boolean isAcknowledged;
    private String sentAt;

}
