package net.bean.java.open.messenger.util;

import net.bean.java.open.messenger.model.User;
import net.bean.java.open.messenger.model.enums.Role;
import net.bean.java.open.messenger.rest.model.user.NewUserInfo;
import net.bean.java.open.messenger.service.UserService;

import java.util.List;
import java.util.Optional;

public class UserCreator {

    private UserCreator() { }

    public static User createUserIfNeeded(UserService userService, String firstName, String lastName, String password) {
        String userName = firstName.toLowerCase() + "." + lastName.toLowerCase();
        return userService.getUserByUserName(userName).orElseGet(() -> userService.tryToCreateUser(new NewUserInfo(userName, firstName, lastName, userName + "@company.com", password))
                .toJavaOptional()
                .map(userInfo -> userService.getUserById(userInfo.getId()))
                .orElseThrow().get());
    }
}
