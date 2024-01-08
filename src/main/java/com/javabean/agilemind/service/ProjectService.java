package com.javabean.agilemind.service;

import com.javabean.agilemind.domain.Project;
import com.javabean.agilemind.domain.Requirement;
import com.javabean.agilemind.domain.UserStory;
import com.javabean.agilemind.dto.ProjectCounts;
import com.javabean.agilemind.dto.UpcomingTask;
import com.javabean.agilemind.exceptions.InvalidRequirementsException;
import com.javabean.agilemind.exceptions.AccessDeniedException;
import org.bson.types.ObjectId;

import java.util.List;

public interface ProjectService {

    List<Project> getProjects(ObjectId userId);

    Project saveProject(Project project, ObjectId userId);

    ProjectCounts getProjectCounts(ObjectId userId);

    List<UpcomingTask> getUpcomingTasks(ObjectId userId, int daysUntilDue);

    List<Requirement> getRequirements(ObjectId projectId);

    Requirement saveRequirement(Requirement requirement, ObjectId userId, ObjectId projectId);

    void deleteRequirement(ObjectId requirementId, ObjectId projectId, ObjectId userId) throws AccessDeniedException;

    List<UserStory> generateUserStoriesFromRequirements(ObjectId projectId, ObjectId userId) throws AccessDeniedException, InvalidRequirementsException;

    Requirement getRequirement(ObjectId requirementId);
}
