package com.devtrack.projects.application.impl;

import com.devtrack.projects.application.ProjectService;
import com.devtrack.projects.application.mapper.input.ProjectInputMapper;
import com.devtrack.projects.application.mapper.output.ProjectOutputMapper;
import com.devtrack.projects.domain.entity.ProjectEntity;
import com.devtrack.projects.domain.repository.ProjectRepository;
import com.devtrack.projects.infraestructure.controller.dto.input.ProjectInputManagerDto;
import com.devtrack.projects.infraestructure.controller.dto.input.ProjectInputSimpleDto;
import com.devtrack.projects.infraestructure.controller.dto.output.ProjectOutputFullDto;
import com.devtrack.tasks.domain.entity.TaskEntity;
import com.devtrack.tasks.domain.repository.TaskRepository;
import com.devtrack.users.application.mapper.input.UserInputMapper;
import com.devtrack.users.application.mapper.output.UserOutputMapper;
import com.devtrack.users.domain.entity.UserEntity;
import com.devtrack.users.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final ProjectInputMapper projectInputMapper;
    private final ProjectOutputMapper projectOutputMapper;
    private final UserInputMapper userInputMapper;
    private final UserOutputMapper userOutputMapper;


    @Override
    public ResponseEntity<String> createProject(ProjectInputSimpleDto projectInputSimpleDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserEntity manager = userRepository.findUserEntitiesByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        ProjectEntity project = new ProjectEntity();
        project.setManager(manager);
        project.setClientName(projectInputSimpleDto.getClientName());
        project.setDescription(projectInputSimpleDto.getDescription());
        project.setProjectName(projectInputSimpleDto.getProjectName());
        projectRepository.save(project);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Project created successfull");
    }

    private void ensureEmailIsUnique(String email) {
        // Si el correo ya existe en la base de datos, MongoDB lanzará un error de duplicado
        Optional<UserEntity> existingUser = userRepository.findUserEntitiesByEmail(email);
        if (existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email " + email + " is already in use");
        }
    }

    @Override
    public List<ProjectOutputFullDto> getAllProjects() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserEntity user = userRepository.findUserEntitiesByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<ProjectEntity> projects = projectRepository
                .findProjectsWhereUserIsManagerOrTeam(user.get_id());
        return projectOutputMapper.projectsToProjectFullDtos(projects);
    }


    @Override
    public  ProjectOutputFullDto getProjectById(String id) {
        ProjectEntity projectEntity = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return projectOutputMapper.entityToOutputFullDto(projectEntity);
    }

    @Override
    public ProjectOutputFullDto updateProject(String id, ProjectInputManagerDto projectInputManagerDto) {

        ProjectEntity projectEntity = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        if(projectInputManagerDto.getManager() != null){
            UserEntity manager = userRepository.findUserEntitiesByEmail(projectInputManagerDto.getManager().getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Manager not found"));
            projectEntity.setManager(manager);
        }

        Optional.ofNullable(projectInputManagerDto.getProjectName())
                .ifPresent(projectEntity::setProjectName);
        Optional.ofNullable(projectInputManagerDto.getDescription())
                .ifPresent(projectEntity::setDescription);
        Optional.ofNullable(projectInputManagerDto.getDescription())
                .ifPresent(projectEntity::setDescription);
        Optional.ofNullable(projectInputManagerDto.getClientName())
                .ifPresent(projectEntity::setClientName);
        

        return projectOutputMapper.entityToOutputFullDto(
                projectRepository.save(projectEntity)
        );
    }

    @Override
    public ProjectOutputFullDto deleteProject(String id) {
        ProjectEntity projectEntity = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        List<TaskEntity> taskEntities = projectEntity.getTasks();

        taskEntities.forEach(taskEntity -> taskEntity.setProject(null));

        taskRepository.saveAll(taskEntities);

        projectRepository.delete(projectEntity);
        return projectOutputMapper.entityToOutputFullDto(projectEntity);
    }

}