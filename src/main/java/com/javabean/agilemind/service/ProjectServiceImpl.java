package com.javabean.agilemind.service;

import com.javabean.agilemind.domain.Project;
import com.javabean.agilemind.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements  ProjectService {
    private ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
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
}
