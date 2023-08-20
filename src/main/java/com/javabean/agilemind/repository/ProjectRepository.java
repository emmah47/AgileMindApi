package com.javabean.agilemind.repository;

import com.javabean.agilemind.domain.Project;

import java.util.List;

public interface ProjectRepository {
    List<Project> getProjects(String login);

    Project saveProject(Project project);
}
