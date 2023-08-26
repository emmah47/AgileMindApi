package com.javabean.agilemind.service;

import com.javabean.agilemind.domain.User;
import com.javabean.agilemind.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Optional<User> getUserByUsername(String username) {
        return Optional.ofNullable(userRepository.getUserByUsername(username));
    }

    @Override
    public User saveUser(User user) {
        return userRepository.saveUser(user);
    }
}
