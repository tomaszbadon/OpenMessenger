package net.bean.java.open.messenger.service;

import net.bean.java.open.messenger.rest.model.ContactInfo;
import net.bean.java.open.messenger.model.entity.User;

import java.util.List;

public interface ContactResource {

    List<ContactInfo> getContacts(User user);

}
