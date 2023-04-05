package net.bean.java.open.messenger.repository.impl;

import lombok.RequiredArgsConstructor;
import net.bean.java.open.messenger.model.User;
import net.bean.java.open.messenger.repository.UserRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final MongoTemplate template;

    @Override
    public Optional<User> findByUserName(String userName) {
        Query query = new Query();
        query.addCriteria(Criteria.where(User.USER_NAME).is(userName));
        return Optional.ofNullable(template.findOne(query, User.class));
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(template.findById(id, User.class));
    }

    @Override
    public User save(User user) {
        return template.save(user);
    }

    @Override
    public List<User> findAll() {
        return template.findAll(User.class);
    }

    @Override
    public void deleteAll() {
        template.remove(new Query(), User.class);
    }
}
