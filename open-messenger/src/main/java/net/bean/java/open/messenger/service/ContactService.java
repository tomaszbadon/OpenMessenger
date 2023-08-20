package net.bean.java.open.messenger.service;

import io.vavr.control.Try;
import net.bean.java.open.messenger.model.User;
import net.bean.java.open.messenger.rest.model.contact.ContactList;

public interface ContactService {

    Try<ContactList> getContacts(Try<User> user);

}
