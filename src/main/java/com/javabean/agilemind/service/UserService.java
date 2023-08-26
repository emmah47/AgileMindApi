package com.javabean.agilemind.service;

import com.javabean.agilemind.domain.User;

import java.util.Optional;

public interface UserService {

    Optional<User> getUserByUsername(String username);

    User saveUser(User user);
}
