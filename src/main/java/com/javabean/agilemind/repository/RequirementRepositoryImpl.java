package com.javabean.agilemind.repository;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

@Repository
public class RequirementRepositoryImpl implements RequirementRepository {
    private MongoOperations mongoOperations;

    public RequirementRepositoryImpl(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }
}
