package net.bean.java.open.messenger.rest.model;

import lombok.Data;

import java.util.List;

@Data
public class UnreadMessagesInfo {

    private List<UnreadMessage> unreadMessages;

}
