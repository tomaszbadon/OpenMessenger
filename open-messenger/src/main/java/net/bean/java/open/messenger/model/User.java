package net.bean.java.open.messenger.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bean.java.open.messenger.model.enums.Role;
import net.bean.java.open.messenger.rest.model.user.NewUserInfo;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotNull;
import java.util.List;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;

    @NotNull
    @Indexed(unique = true)
    private String userName;
    public static final String USER_NAME = "userName";

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String password;

    @NotNull
    @Indexed(unique = true)
    private String email;

    private String avatar;

    private String status;

    private List<Role> roles;

    public User(NewUserInfo newUserInfo, PasswordEncoder passwordEncoder) {
        userName = newUserInfo.getUserName();
        firstName = newUserInfo.getFirstName();
        lastName = newUserInfo.getLastName();
        email = newUserInfo.getEmail();
        password = passwordEncoder.encode(newUserInfo.getPassword());
        roles = List.of(Role.ROLE_USER);
    }

}