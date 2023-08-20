package com.javabean.agilemind.controller;

import com.javabean.agilemind.domain.Project;
import com.javabean.agilemind.service.ProjectService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("projects")
public class ProjectController {
    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping(path = "", produces = "application/json")
    public List<Project> getProjects(@AuthenticationPrincipal OAuth2User principal) {
        List<Project> projects = projectService.getProjects(getLogin(principal));
        return projects;
    }

    private static String getLogin(OAuth2User principal) {
        return principal.getAttribute("login");
    }

    @PostMapping("")
    public Project createProject(@RequestBody Project project, @AuthenticationPrincipal OAuth2User principal) {
        return projectService.saveProject(project, getLogin(principal));
    }

    @GetMapping("/csrf-token1")
    public CsrfToken csrf(CsrfToken token) {
        return token;
    }

}
