package net.bean.java.open.messenger.rest.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bean.java.open.messenger.rest.annotation.UniqueUserNameConstraint;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUserInfo {

    @NotBlank
    @Length(min = 6, max = 20, message = "The username is too short or too long")
    @UniqueUserNameConstraint
    private String userName;

    @NotBlank
    @Length(min = 2, max = 20, message = "The first name is too short or too long")
    private String firstName;

    @NotBlank
    @Length(min = 2, max = 20, message = "The last name is too short or too long")
    private String lastName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$", message = "The password has to have at least 8 characters")
    private String password;

}
