package net.bean.java.open.messenger.service;

import io.vavr.control.Try;
import net.bean.java.open.messenger.model.User;
import net.bean.java.open.messenger.rest.model.ContactList;

public interface ContactResource {

    ContactList getContacts(Try<User> user);

}
