package com.javabean.agilemind.service;

import com.javabean.agilemind.domain.UserStory;
import com.javabean.agilemind.exceptions.InvalidRequirementsException;

import java.util.List;

public interface ChatGptService {
    List<UserStory> generateUserStories(String requirements) throws InvalidRequirementsException;
}
