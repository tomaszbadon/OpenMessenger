package net.bean.java.open.messenger.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.bean.java.open.messenger.data.jpa.model.User;

@Data
@AllArgsConstructor
public class Contact {

    private final Long id;
    private final String userName;
    private final String firstName;
    private final String lastName;
    private final String email;
    private String avatar;
    private String status;

    public static Contact of(User user) {
        return new Contact(user.getId(), user.getUserName(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getAvatar(), user.getStatus());
    }

}