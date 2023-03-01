package net.bean.java.open.messenger.service;

import io.vavr.control.Try;
import net.bean.java.open.messenger.model.entity.Role;
import net.bean.java.open.messenger.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User saveUser(User save);

    Role saveRole(Role save);

    void addRoleToUser(String userName, String roleName);

    Optional<User> getUser(String userName);

    Optional<User> getUser(Long id);

    Try<User> tryToGetUser(Long id);

    List<User> getUsers();

}
