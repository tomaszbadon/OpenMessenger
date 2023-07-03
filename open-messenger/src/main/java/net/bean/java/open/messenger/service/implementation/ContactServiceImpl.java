package net.bean.java.open.messenger.service.implementation;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import net.bean.java.open.messenger.model.User;
import net.bean.java.open.messenger.rest.model.ContactInfo;
import net.bean.java.open.messenger.rest.model.ContactList;
import net.bean.java.open.messenger.service.ContactService;
import net.bean.java.open.messenger.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final UserService userService;

    @Override
    public Try<ContactList> getContacts(Try<User> user) {
        return user.map(unwrappedUser -> userService.getUsers()
                   .stream()
                   .filter(repoUser -> !Objects.equals(unwrappedUser, repoUser))
                   .map(ContactInfo::new)
                   .collect(Collectors.toList())).map(ContactList::new);
    }
}
