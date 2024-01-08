package com.javabean.agilemind.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("userStories")
public class UserStory {
    @Id
    @JsonSerialize(using= ToStringSerializer.class)
    private ObjectId id;

    @JsonSerialize(using= ToStringSerializer.class)
    private ObjectId projectId;
    private String title;
    private String description;
    private UserStoryStatus status;
    private int points; // story points, must be in fibonacci
    @JsonSerialize(using= ToStringSerializer.class)
    private ObjectId assignedUserId;
}
