package net.bean.java.open.messenger.service;

import net.bean.java.open.messenger.data.jpa.model.Role;
import net.bean.java.open.messenger.data.jpa.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User save);
    Role saveRole(Role save);
    void addRoleToUser(String userName, String roleName);
    Optional<User> getUser(String userName);
    Optional<User> getUser(Long id);
    List<User> getUsers();
}
