package net.bean.java.open.messenger.rest.model.contact;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.bean.java.open.messenger.model.User;

@RequiredArgsConstructor
@Data
public class ContactInfo {
    private final String id;
    private final String userName;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String status;

    public ContactInfo(User user) {
        id = user.getId();
        userName = user.getUserName();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        email = user.getEmail();
        status = user.getStatus();
    }

}