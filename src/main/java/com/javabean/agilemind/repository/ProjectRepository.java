package com.javabean.agilemind.repository;

import com.javabean.agilemind.domain.Project;
import com.javabean.agilemind.domain.Requirement;

import java.util.List;

public interface ProjectRepository {
    List<Project> getProjects(String login);

    Project saveProject(Project project);

    List<Requirement> getRequirements(String projectId);
}
