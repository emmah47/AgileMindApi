package com.javabean.agilemind.service;

import com.javabean.agilemind.domain.Project;
import com.javabean.agilemind.domain.Requirement;
import com.javabean.agilemind.domain.UserStory;
import com.javabean.agilemind.exceptions.InvalidRequirementsException;
import com.javabean.agilemind.exceptions.PermissionDeniedException;
import com.javabean.agilemind.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ProjectServiceImpl implements  ProjectService {
    private ProjectRepository projectRepository;
    private ChatGptService gpt;

    public ProjectServiceImpl(ProjectRepository projectRepository, ChatGptService gpt) {
        this.projectRepository = projectRepository;
        this.gpt = gpt;
    }

    @Override
    public List<Project> getProjects(String login) {
        List<Project> projects = this.projectRepository.getProjects(login);
        return projects;
    }

    @Override
    public Project saveProject(Project project, String login) {
        project.setOwner(login);
        return projectRepository.saveProject(project);
    }

    @Override
    public List<Requirement> getRequirements(String projectId) {
        return projectRepository.getRequirements(projectId);
    }

    @Override
    public Requirement saveRequirement(Requirement requirement, String userId, String projectId) {
        // TODO
        return null;
    }

    @Override
    public Requirement deleteRequirement(String requirementId, String projectId, String userId) {
        // TODO
        return null;
    }

    @Override
    public List<UserStory> generateUserStoriesFromRequirements(String projectId, String userId) throws PermissionDeniedException, InvalidRequirementsException {
        this.checkPermission(userId, projectId);

        // making sure requirements are not empty
        List<Requirement> requirements = this.getRequirements(projectId);

        // put all requirements into one big string
        String requirementsPrompt = "";
        for (Requirement r : requirements) {
            requirementsPrompt = requirementsPrompt.concat(", " + r.getContent());
        }

        List<UserStory> userStories = gpt.generateUserStories(requirementsPrompt);

        return userStories;
    }

    private void checkPermission(String userId, String projectId) throws PermissionDeniedException {
        // TODO
    }
}
