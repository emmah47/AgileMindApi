package com.javabean.agilemind.service;

import com.javabean.agilemind.domain.Project;

import java.util.List;

public interface ProjectService {

    List<Project> getProjects(String login);

    Project saveProject(Project project, String login);
}
