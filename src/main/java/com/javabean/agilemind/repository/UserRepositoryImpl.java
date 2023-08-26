package com.javabean.agilemind.repository;

import com.javabean.agilemind.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final MongoOperations mongoOperations;
    @Override
    public User loadUser(String id) {
        return mongoOperations.findById(id, User.class);
    }

    @Override
    public User saveUser(User user) {
        return mongoOperations.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        Query query = new Query(Criteria.where("username").is(username));
        return mongoOperations.findOne(query, User.class);
    }
}
