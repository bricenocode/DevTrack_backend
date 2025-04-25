package com.devtrack.projects.infraestructure.controller;


import com.devtrack.projects.application.ProjectService;
import com.devtrack.projects.infraestructure.controller.dto.input.ProjectInputManagerDto;
import com.devtrack.projects.infraestructure.controller.dto.input.ProjectInputSimpleDto;
import com.devtrack.projects.infraestructure.controller.dto.output.ProjectOutputFullDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    ResponseEntity<String> createProject(
            @RequestBody
            @Valid
            ProjectInputSimpleDto projectInputManagerDto){
        return projectService.createProject(projectInputManagerDto);
    }
    @GetMapping
    List<ProjectOutputFullDto> getAllProjects(){
        return projectService.getAllProjects();
    }
    
    @GetMapping("/{projectId}")
    ProjectOutputFullDto getProjectById(
            @PathVariable
            String projectId){
        return projectService.getProjectById(projectId);
    }
    @PutMapping("/{projectId}")
    ProjectOutputFullDto updateProject(
            @PathVariable
            String projectId,
            @RequestBody
            ProjectInputManagerDto projectInputManagerDto){
        System.out.println(projectId);
        return projectService.updateProject(projectId, projectInputManagerDto);
    }
    @DeleteMapping("/{projectId}")
    ProjectOutputFullDto deleteProject(
            @PathVariable
            String projectId){
        return projectService.deleteProject(projectId);
    }
}