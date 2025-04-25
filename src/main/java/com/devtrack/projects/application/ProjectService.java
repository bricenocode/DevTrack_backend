package com.devtrack.projects.application;

import com.devtrack.projects.infraestructure.controller.dto.input.ProjectInputManagerDto;
import com.devtrack.projects.infraestructure.controller.dto.input.ProjectInputSimpleDto;
import com.devtrack.projects.infraestructure.controller.dto.output.ProjectOutputFullDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProjectService {
    ResponseEntity<String> createProject(ProjectInputSimpleDto projectInputSimpleDto);
    List<ProjectOutputFullDto> getAllProjects();
    ProjectOutputFullDto getProjectById(String id);
    ProjectOutputFullDto updateProject(String id, ProjectInputManagerDto projectInputManagerDto);
    ProjectOutputFullDto deleteProject(String id);
}
