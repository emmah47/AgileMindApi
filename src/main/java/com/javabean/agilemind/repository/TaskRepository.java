package com.javabean.agilemind.repository;

import com.javabean.agilemind.domain.Task;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

public interface TaskRepository {
    List<Task> getRecentTasks(ObjectId userId, Date dueDate);

}
