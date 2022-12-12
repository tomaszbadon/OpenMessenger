package net.bean.java.open.messenger.entity;

import lombok.Data;

@Data
public class InputMessagePayload {
    private String message;
    private Long recipient;
}
