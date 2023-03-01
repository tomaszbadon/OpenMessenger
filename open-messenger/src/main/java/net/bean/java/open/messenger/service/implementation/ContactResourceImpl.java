package net.bean.java.open.messenger.service.implementation;

import lombok.RequiredArgsConstructor;
import net.bean.java.open.messenger.model.entity.User;
import net.bean.java.open.messenger.rest.model.ContactInfo;
import net.bean.java.open.messenger.rest.model.ContactList;
import net.bean.java.open.messenger.service.ContactResource;
import net.bean.java.open.messenger.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactResourceImpl implements ContactResource {

    private final UserService userService;

    @Override
    public ContactList getContacts(User user) {
        return new ContactList(userService.getUsers()
                                          .stream()
                                          .filter(u -> !Objects.equals(user, u))
                                          .map(ContactInfo::new)
                                          .collect(Collectors.toList()));
    }
}
