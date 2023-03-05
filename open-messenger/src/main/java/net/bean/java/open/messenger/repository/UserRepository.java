package net.bean.java.open.messenger.repository;

import net.bean.java.open.messenger.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    List<User> findByUserName(String userName);

}
