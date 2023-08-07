package net.bean.java.open.messenger.rest.model.contact;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ContactList {
    private List<ContactInfo> contacts;

}
