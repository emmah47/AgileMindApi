package com.javabean.agilemind.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document("user")
public class User {

    @Id
    @JsonSerialize(using= ToStringSerializer.class)
    private ObjectId id;

    private String username;
    private String password;
    private String name;
    private String email;
    private String role;
    private String imageUrl;

}
