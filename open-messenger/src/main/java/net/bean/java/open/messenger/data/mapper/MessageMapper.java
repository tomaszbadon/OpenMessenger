package net.bean.java.open.messenger.data.mapper;

import net.bean.java.open.messenger.data.dto.MessageDTO;
import net.bean.java.open.messenger.data.jpa.model.Message;

import java.text.SimpleDateFormat;

public class MessageMapper {

    private static SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public MessageDTO mapEntityToDto(Message message) {
        MessageDTO dto = new MessageDTO();
        dto.setMessage(message.getContent());
        dto.setId(message.getId());
        dto.setRecipient(message.getRecipient().getId());
        dto.setSender(message.getSender().getId());
        dto.setAcknowledged(message.isAcknowledged());
        dto.setSentAt(format.format(message.getSentAt()));
        return dto;
    }

}
