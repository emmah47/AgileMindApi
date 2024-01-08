package com.javabean.agilemind.repository;

import com.javabean.agilemind.domain.Task;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class TaskRepositoryImpl implements TaskRepository {
    private MongoOperations mongoOperations;

    public TaskRepositoryImpl(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }
    @Override
    public List<Task> getRecentTasks(ObjectId userId, Date dueDate) {
        Query query = new Query(where("assignedUserId").is(userId).and("dueDate").lte(dueDate).and("completed").is(false));
        return mongoOperations.find(query, Task.class);
    }
}
