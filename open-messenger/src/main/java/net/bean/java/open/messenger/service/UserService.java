package net.bean.java.open.messenger.service;

import io.vavr.control.Try;
import net.bean.java.open.messenger.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User saveUser(User save);

    Optional<User> getUserByUserName(String userName);

    Optional<User> getUserById(String id);

    Try<User> tryToGetUserById(String id);

    List<User> getUsers();

    void changePassword(String id, String password);

}
