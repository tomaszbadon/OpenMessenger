package net.bean.java.open.messenger.service.implementation;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import net.bean.java.open.messenger.model.User;
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
    public ContactList getContacts(Try<User> user) {
       var contactList = user.map(unwrappedUser -> userService.getUsers()
                .stream()
                .filter(repoUser -> !Objects.equals(unwrappedUser, repoUser))
                .map(ContactInfo::new)
                .collect(Collectors.toList())).map(ContactList::new);
        return contactList.get();
    }
}
