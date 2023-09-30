package net.bean.java.open.messenger.util;

import net.bean.java.open.messenger.model.User;
import net.bean.java.open.messenger.rest.model.user.NewUserInfo;
import net.bean.java.open.messenger.service.UserService;

public class UserCreator {

    private UserCreator() { }

    public static User createUserIfNeeded(UserService userService, String firstName, String lastName, String password, String avatar) {
        String userName = firstName.toLowerCase() + "." + lastName.toLowerCase();
        return userService.getUserByUserName(userName).orElseGet(() -> userService.tryToCreateUser(new NewUserInfo(userName, firstName, lastName, userName + "@company.com", password), avatar)
                .toJavaOptional()
                .flatMap(userInfo -> userService.getUserById(userInfo.getId()))
                .orElseThrow());
    }
}
