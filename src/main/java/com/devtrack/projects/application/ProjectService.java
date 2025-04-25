package com.devtrack.projects.application;

import com.devtrack.projects.infraestructure.controller.dto.input.ProjectInputManagerDto;
import com.devtrack.projects.infraestructure.controller.dto.output.ProjectOutputFullDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProjectService {
    ProjectOutputFullDto createProject(ProjectInputManagerDto projectInputManagerDto);
    List<ProjectOutputFullDto> getAllProjects();
    ProjectOutputFullDto getProjectById(String id);
    ProjectOutputFullDto updateProject(String id, ProjectInputManagerDto projectInputManagerDto);
    ProjectOutputFullDto deleteProject(String id);
}
