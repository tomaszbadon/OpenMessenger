package net.bean.java.open.messenger.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.bean.java.open.messenger.model.jpa.User;

@RequiredArgsConstructor
@Data
public class ContactInfo {

    private final Long id;
    private final String userName;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String avatar;
    private final String status;

    public ContactInfo(User user) {
        id = user.getId();
        userName = user.getUserName();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        email = user.getEmail();
        avatar = user.getAvatar();
        status = user.getStatus();
    }

}