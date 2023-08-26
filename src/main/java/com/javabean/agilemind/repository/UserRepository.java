package com.javabean.agilemind.repository;

import com.javabean.agilemind.domain.User;

import java.util.Optional;

public interface UserRepository {
    User loadUser(String id);

    User saveUser(User user);

    User getUserByUsername(String username);
}
