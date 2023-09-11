package com.javabean.agilemind.controller;

import com.javabean.agilemind.domain.*;
import com.javabean.agilemind.exceptions.InvalidRequirementsException;
import com.javabean.agilemind.exceptions.PermissionDeniedException;
import com.javabean.agilemind.security.CustomUserDetails;
import com.javabean.agilemind.service.ProjectService;
import org.bson.types.ObjectId;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
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
        List<Project> projects = projectService.getProjects(getUserObjectId(principal));
        return projects;
    }

    @PostMapping("")
    public Project createProject(@RequestBody Project project, @AuthenticationPrincipal OAuth2User principal) {
        return projectService.saveProject(project, getUserObjectId(principal));
    }

    private static ObjectId getUserObjectId(OAuth2User principal) {
        return ((CustomUserDetails) principal).getId();
    }

    @GetMapping("{projectId}/requirements")
    public List<Requirement> getRequirements(@PathVariable String projectId) {
        return projectService.getRequirements(new ObjectId(projectId));
    }

    @PostMapping("{projectId}/requirements")
    public Requirement addRequirements(@RequestBody Requirement requirement, @AuthenticationPrincipal OAuth2User principal, @PathVariable String projectId) {
        return projectService.saveRequirement(requirement, getUserObjectId(principal), new ObjectId(projectId));
    }

    @DeleteMapping("{projectId}/requirements/{requirementId}/delete")
    public Requirement deleteRequirements(@PathVariable String projectId, @PathVariable String requirementId, @AuthenticationPrincipal OAuth2User principal) {
        return projectService.deleteRequirement(new ObjectId(projectId), new ObjectId(requirementId), getUserObjectId(principal));
    }

    @GetMapping("{projectId}/user-stories/generate")
    public List<UserStory> generateUserStoriesFromRequirements(@PathVariable String projectId, @AuthenticationPrincipal OAuth2User principal) {

        try {
            List<UserStory> userStories =  projectService.generateUserStoriesFromRequirements(new ObjectId(projectId), getUserObjectId(principal));
            return userStories;
        } catch (PermissionDeniedException e) {
            //TODO
        } catch (InvalidRequirementsException e) {
            //TODO
        }
        return null; // TODO: all returns should be inside catch blocks, after implementing those, delete this line
    }

}
