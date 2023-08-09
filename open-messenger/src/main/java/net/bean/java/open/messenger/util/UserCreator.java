package net.bean.java.open.messenger.util;

import net.bean.java.open.messenger.model.User;
import net.bean.java.open.messenger.model.enums.Role;
import net.bean.java.open.messenger.service.UserService;

import java.util.List;

public class UserCreator {

    private UserCreator() { }

    public static User createUserIfNeeded(UserService userService, String firstName, String lastName, String password, String avatar, String status) {
        List<Role> roles = List.of(Role.ROLE_USER);
        String userName = firstName.toLowerCase() + "." + lastName.toLowerCase();
        return userService.getUserByUserName(userName).orElseGet(() -> {
            return userService.saveUser(new User(null, userName, firstName, lastName, password, userName + "@company.com", avatar, status, roles));
        });
    }
}
