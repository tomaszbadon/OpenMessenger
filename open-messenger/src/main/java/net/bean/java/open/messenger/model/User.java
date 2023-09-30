package net.bean.java.open.messenger.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bean.java.open.messenger.model.enums.Role;
import net.bean.java.open.messenger.rest.model.user.NewUserInfo;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    public static final String USER_NAME = "userName";

    @Id
    private String id;

    @NotNull
    @Length(min = 6, max = 20, message = "The username is too short or too long")
    private String userName;

    @NotNull
    @Length(min = 2, max = 20, message = "The first name is too short or too long")
    private String firstName;

    @NotNull
    @Length(min = 2, max = 20, message = "The last name is too short or too long")
    private String lastName;

    @NotNull
    private String password;

    @NotNull
    @Indexed(unique = true)
    @Email
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