package com.javabean.agilemind.service;

import com.javabean.agilemind.domain.Project;
import com.javabean.agilemind.domain.Requirement;
import com.javabean.agilemind.domain.UserStory;
import com.javabean.agilemind.exceptions.InvalidRequirementsException;
import com.javabean.agilemind.exceptions.PermissionDeniedException;
import com.javabean.agilemind.repository.ProjectRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

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
    public List<Project> getProjects(ObjectId userId) {
        List<Project> projects = this.projectRepository.getProjects(userId);
        return projects;
    }

    @Override
    public Project saveProject(Project project, ObjectId userId) {
        project.setOwnerId(userId);
        return projectRepository.saveProject(project);
    }

    @Override
    public List<Requirement> getRequirements(ObjectId projectId) {
        return projectRepository.getRequirements(projectId);
    }

    @Override
    public Requirement saveRequirement(Requirement requirement, ObjectId userId, ObjectId projectId) {
        requirement.setUserId(userId);
        requirement.setProjectId(projectId);
        return projectRepository.saveRequirement(requirement);
    }

    @Override
    public Requirement deleteRequirement(ObjectId requirementId, ObjectId projectId, ObjectId userId) {
        // TODO
        return null;
    }

    @Override
    public List<UserStory> generateUserStoriesFromRequirements(ObjectId projectId, ObjectId userId) throws PermissionDeniedException, InvalidRequirementsException {
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

    private void checkPermission(ObjectId userId, ObjectId projectId) throws PermissionDeniedException {
        Project project = projectRepository.getProject(projectId);
        if (!userId.equals(project.getOwnerId())) {
            throw new PermissionDeniedException();
        }
    }
}
