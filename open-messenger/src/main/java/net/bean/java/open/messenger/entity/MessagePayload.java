package net.bean.java.open.messenger.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MessagePayload {

    private List<OutputMessagePayload> messages;

    private int page;

}
