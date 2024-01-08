package com.javabean.agilemind.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document("task")
public class Task {
    @Id
    @JsonSerialize(using= ToStringSerializer.class)
    private ObjectId id;

    @JsonSerialize(using= ToStringSerializer.class)
    private ObjectId projectId;
    @JsonSerialize(using= ToStringSerializer.class)
    private ObjectId userStoryId;
    @JsonSerialize(using= ToStringSerializer.class)
    private ObjectId assignedUserId;

    private String content;
    private Boolean completed;
    private Date startDate;
    private Date dueDate;
}
