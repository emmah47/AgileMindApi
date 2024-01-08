package com.javabean.agilemind.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("requirement")
public class Requirement {
    @Id
    @JsonSerialize(using= ToStringSerializer.class)
    private ObjectId id;

    private String content;
    @JsonSerialize(using= ToStringSerializer.class)
    private ObjectId userId;
    @JsonSerialize(using= ToStringSerializer.class)
    private ObjectId projectId;
}
