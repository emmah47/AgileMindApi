package com.javabean.agilemind.service;

import com.javabean.agilemind.domain.Project;
import com.javabean.agilemind.domain.Requirement;
import com.javabean.agilemind.domain.Task;
import com.javabean.agilemind.domain.UserStory;
import com.javabean.agilemind.dto.ProjectCounts;
import com.javabean.agilemind.dto.UpcomingTask;
import com.javabean.agilemind.exceptions.InvalidRequirementsException;
import com.javabean.agilemind.exceptions.AccessDeniedException;
import com.javabean.agilemind.repository.ProjectRepository;
import com.javabean.agilemind.repository.TaskRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class ProjectServiceImpl implements  ProjectService {
    private ProjectRepository projectRepository;
    private TaskRepository taskRepository;
    private ChatGptService gpt;

    public ProjectServiceImpl(ProjectRepository projectRepository, TaskRepository taskRepository, ChatGptService gpt) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
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
        project.setCreationDate(Date.from(Instant.now()));
        project.setLastOpenedDate(Date.from(Instant.now()));
        return projectRepository.saveProject(project);
    }

    @Override
    public ProjectCounts getProjectCounts(ObjectId userId) {
        ProjectCounts count = this.projectRepository.getProjectCounts(userId);
        return count;
    }

    @Override
    public Project getProjectById(String projectId, ObjectId userId) throws AccessDeniedException {
        ObjectId projectIdObj = new ObjectId(projectId);
        checkPermission(userId, projectIdObj);
        return projectRepository.getProject(projectIdObj);
    }

    @Override
    public List<UpcomingTask> getUpcomingTasks(ObjectId userId, int daysUntilDue) {
        Date latestDueDate = Date.from(Instant.now().plus(Duration.ofDays(daysUntilDue)));
        List<Task> recentTasks = taskRepository.getRecentTasks(userId, latestDueDate);

        List<UpcomingTask> upcomingTasks = new ArrayList<>();
        for (Task task : recentTasks) {
            UpcomingTask upcomingTask = new UpcomingTask();
            upcomingTask.setTaskContent(task.getContent());

            long millisecondsDiff = task.getDueDate().getTime() - Date.from(Instant.now()).getTime();
            int daysBetween = (int) Math.ceil(millisecondsDiff / (1000.0 * 60.0 * 60.0 * 24.0));
            upcomingTask.setDaysUntilDue(daysBetween);

            upcomingTask.setUserStoryId(task.getUserStoryId());
            upcomingTask.setProjectId(task.getProjectId());

            Project project = projectRepository.getProject(task.getProjectId());
            upcomingTask.setProjectName(project.getName());

            upcomingTasks.add(upcomingTask);
        }
        Collections.sort(upcomingTasks);

        return upcomingTasks;
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
