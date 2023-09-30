package net.bean.java.open.messenger.service;

import io.vavr.control.Try;
import net.bean.java.open.messenger.model.User;
import net.bean.java.open.messenger.rest.model.user.NewUserInfo;
import net.bean.java.open.messenger.rest.model.user.UserInfo;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Try<UserInfo> tryToCreateUser(NewUserInfo newUserInfo);

    Try<UserInfo> tryToCreateUser(NewUserInfo newUserInfo, String base64EncodedAvatar);

    Optional<User> getUserByUserName(String userName);

    Optional<User> getUserById(String id);

    Try<User> tryToGetUserById(String id);

    List<User> getUsers();

    void changePassword(String id, String password);

    byte[] getAvatarByUserId(String userId);

}
