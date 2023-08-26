package com.javabean.agilemind.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {

    private Long id;

    private String username;
    private String password;
    private String name;
    private String email;
    private String role;
    private String imageUrl;

    private String providerId;

    public User(String username, String password, String name, String email, String role, String imageUrl, String providerId) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
        this.imageUrl = imageUrl;
        this.providerId = providerId;
    }
}
