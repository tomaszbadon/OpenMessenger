package net.bean.java.open.messenger.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.bean.java.open.messenger.data.dto.OutputMessageDTO;

import java.util.List;

@Data
@AllArgsConstructor
public class MessagesPage {

    private List<OutputMessageDTO> messages;

    private int page;

}
