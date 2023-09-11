package com.javabean.agilemind.service;

import com.javabean.agilemind.domain.Project;
import com.javabean.agilemind.domain.Requirement;
import com.javabean.agilemind.domain.UserStory;
import com.javabean.agilemind.exceptions.InvalidRequirementsException;
import com.javabean.agilemind.exceptions.PermissionDeniedException;
import org.bson.types.ObjectId;

import java.util.List;

public interface ProjectService {

    List<Project> getProjects(ObjectId userId);

    Project saveProject(Project project, ObjectId userId);

    List<Requirement> getRequirements(ObjectId projectId);

    Requirement saveRequirement(Requirement requirement, ObjectId userId, ObjectId projectId);

    Requirement deleteRequirement(ObjectId requirementId, ObjectId projectId, ObjectId userId);

    List<UserStory> generateUserStoriesFromRequirements(ObjectId projectId, ObjectId userId) throws PermissionDeniedException, InvalidRequirementsException;
}
