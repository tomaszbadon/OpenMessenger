package net.bean.java.open.messenger.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ContactList {
    private List<ContactInfo> contacts;

}
