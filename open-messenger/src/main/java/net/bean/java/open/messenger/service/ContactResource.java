package net.bean.java.open.messenger.service;

import net.bean.java.open.messenger.model.User;
import net.bean.java.open.messenger.rest.model.ContactList;

public interface ContactResource {

    ContactList getContacts(User user);

}
