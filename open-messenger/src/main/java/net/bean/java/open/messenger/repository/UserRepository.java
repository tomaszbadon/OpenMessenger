package net.bean.java.open.messenger.repository;

import net.bean.java.open.messenger.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findByUserName(String userName);
    Optional<User> findById(String id);
    List<User> findAll();
    User save(User user);
    void deleteAll();
}
