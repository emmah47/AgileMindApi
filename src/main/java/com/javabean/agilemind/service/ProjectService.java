package com.javabean.agilemind.service;

import com.javabean.agilemind.domain.Project;
import com.javabean.agilemind.domain.Requirement;
import com.javabean.agilemind.domain.UserStory;
import com.javabean.agilemind.exceptions.InvalidRequirementsException;
import com.javabean.agilemind.exceptions.PermissionDeniedException;

import java.util.List;

public interface ProjectService {

    List<Project> getProjects(String login);

    Project saveProject(Project project, String login);

    List<Requirement> getRequirements(String projectId);

    Requirement saveRequirement(Requirement requirement, String userId, String projectId);

    Requirement deleteRequirement(String requirementId, String projectId, String userId);

    List<UserStory> generateUserStoriesFromRequirements(String projectId, String userId) throws PermissionDeniedException, InvalidRequirementsException;
}
