package com.javabean.agilemind.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document("project")
public class Project {
    @Id
    @JsonSerialize(using= ToStringSerializer.class)
    private ObjectId id;

    private String name;
    private String description;
    private ProjectStatus status;
    private ObjectId ownerId;
    private List<ObjectId> collaboratorIds;

    private Date creationDate;
    private Date lastOpenedDate;

}
