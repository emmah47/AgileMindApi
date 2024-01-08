package com.javabean.agilemind.repository;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

@Repository
public class UserStoryRepositoryImpl implements UserStoryRepository {
    private MongoOperations mongoOperations;

    public UserStoryRepositoryImpl(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }
}
