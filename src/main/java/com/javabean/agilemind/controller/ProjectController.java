package com.javabean.agilemind.controller;

import com.javabean.agilemind.domain.*;
import com.javabean.agilemind.exceptions.InvalidRequirementsException;
import com.javabean.agilemind.exceptions.PermissionDeniedException;
import com.javabean.agilemind.security.CustomUserDetails;
import com.javabean.agilemind.service.ProjectService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @PostMapping("")
    public Project createProject(@RequestBody Project project, @AuthenticationPrincipal OAuth2User principal) {
        return projectService.saveProject(project, getLogin(principal));
    }

    private static String getLogin(OAuth2User principal) {
        return ((CustomUserDetails) principal).getUsername();
    }

    @GetMapping("{projectId}/requirements")
    public List<Requirement> getRequirements(@PathVariable String projectId) {
        return projectService.getRequirements(projectId);
    }

    @PostMapping("{projectId}/requirements")
    public Requirement addRequirements(@RequestBody Requirement requirement, @AuthenticationPrincipal OAuth2User principal, @PathVariable String projectId) {
        return projectService.saveRequirement(requirement, projectId, getLogin(principal));
    }

    @DeleteMapping("{projectId}/requirements/{requirementId}/delete")
    public Requirement deleteRequirements(@PathVariable String projectId, @PathVariable String requirementId, @AuthenticationPrincipal OAuth2User principal) {
        return projectService.deleteRequirement(projectId, requirementId, getLogin(principal));
    }

    @GetMapping("{projectId}/user-stories/generate")
    public List<UserStory> generateUserStoriesFromRequirements(@PathVariable String projectId, @AuthenticationPrincipal OAuth2User principal) {

        try {
            List<UserStory> userStories =  projectService.generateUserStoriesFromRequirements(projectId, getLogin(principal));
            return userStories;
        } catch (PermissionDeniedException e) {
            //TODO
        } catch (InvalidRequirementsException e) {
            //TODO
        }
        return null; // TODO: all returns should be inside catch blocks, after implementing those, delete this line
    }

}
