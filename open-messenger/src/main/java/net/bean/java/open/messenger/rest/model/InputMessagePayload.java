package net.bean.java.open.messenger.rest.model;

import lombok.Data;

@Data
public class InputMessagePayload {
    private String message;
    private Long recipient;
}
