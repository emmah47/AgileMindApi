package com.javabean.agilemind.service;

import com.javabean.agilemind.domain.Project;
import com.javabean.agilemind.domain.Requirement;
import com.javabean.agilemind.domain.UserStory;
import com.javabean.agilemind.dto.ProjectCounts;
import com.javabean.agilemind.exceptions.InvalidRequirementsException;
import com.javabean.agilemind.exceptions.AccessDeniedException;
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
    public ProjectCounts getProjectCounts(ObjectId userId) {
        ProjectCounts count = this.projectRepository.getProjectCounts(userId);
        return count;
    }

    @Override
    public List<Requirement> getRequirements(ObjectId projectId) {
        return projectRepository.getRequirements(projectId);
    }

    @Override
    public Requirement getRequirement(ObjectId requirementId) {
        return projectRepository.getRequirement(requirementId);
    }

    @Override
    public Requirement saveRequirement(Requirement requirement, ObjectId userId, ObjectId projectId) {
        requirement.setUserId(userId);
        requirement.setProjectId(projectId);
        return projectRepository.saveRequirement(requirement);
    }

    @Override
    public void deleteRequirement(ObjectId requirementId, ObjectId projectId, ObjectId userId) throws AccessDeniedException {
        try {
            Requirement requirement = this.getRequirement(requirementId);
            if (!requirement.getUserId().equals(userId)) {
                throw new AccessDeniedException();
            }
        } catch (AccessDeniedException e) {
            checkPermission(userId, projectId);
        }

        projectRepository.deleteRequirement(requirementId);
    }

    @Override
    public List<UserStory> generateUserStoriesFromRequirements(ObjectId projectId, ObjectId userId) throws AccessDeniedException, InvalidRequirementsException {
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

    private void checkPermission(ObjectId userId, ObjectId projectId) throws AccessDeniedException {
        Project project = projectRepository.getProject(projectId);
        if (!userId.equals(project.getOwnerId())) {
            throw new AccessDeniedException();
        }
    }
}
