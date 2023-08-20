package com.javabean.agilemind.domain;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("project")
public class Project {
    @Id
    private ObjectId id;
    private String name;
    private String description;
    private String owner;

}
