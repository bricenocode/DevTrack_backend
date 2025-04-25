package com.devtrack.projects.application.impl;

import com.devtrack.projects.application.ProjectService;
import com.devtrack.projects.application.mapper.input.ProjectInputMapper;
import com.devtrack.projects.application.mapper.output.ProjectOutputMapper;
import com.devtrack.projects.domain.entity.ProjectEntity;
import com.devtrack.projects.domain.repository.ProjectRepository;
import com.devtrack.projects.infraestructure.controller.dto.input.ProjectInputManagerDto;
import com.devtrack.projects.infraestructure.controller.dto.output.ProjectOutputFullDto;
import com.devtrack.tasks.domain.entity.TaskEntity;
import com.devtrack.tasks.domain.repository.TaskRepository;
import com.devtrack.users.application.mapper.input.UserInputMapper;
import com.devtrack.users.application.mapper.output.UserOutputMapper;
import com.devtrack.users.domain.entity.UserEntity;
import com.devtrack.users.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ProjectOutputFullDto createProject(ProjectInputManagerDto projectInputManagerDto) {

        UserEntity manager = userRepository.findById(projectInputManagerDto.getManager())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        ProjectEntity project = new ProjectEntity();
        project.setManager(manager);
        project.setClientName(projectInputManagerDto.getClientName());
        project.setDescription(projectInputManagerDto.getDescription());
        project.setProjectName(projectInputManagerDto.getProjectName());

        return projectOutputMapper.entityToOutputFullDto(
                projectRepository.save(project));
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
        return  projectRepository.findAll()
                .stream()
                .map(projectOutputMapper::entityToOutputFullDto)
                .toList();
    }

    @Override
    public ProjectOutputFullDto getProjectById(String id) {
        ProjectEntity projectEntity = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return projectOutputMapper.entityToOutputFullDto(projectEntity);
    }

    @Override
    public ProjectOutputFullDto updateProject(String id, ProjectInputManagerDto projectInputManagerDto) {

        ProjectEntity projectEntity = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        UserEntity manager = userRepository.findById(projectInputManagerDto.getManager())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Manager not found"));

        Optional.ofNullable(projectInputManagerDto.getProjectName())
                .ifPresent(projectEntity::setProjectName);
        Optional.ofNullable(projectInputManagerDto.getDescription())
                .ifPresent(projectEntity::setDescription);
        Optional.ofNullable(projectInputManagerDto.getDescription())
                .ifPresent(projectEntity::setDescription);
        Optional.ofNullable(projectInputManagerDto.getClientName())
                .ifPresent(projectEntity::setClientName);
        projectEntity.setManager(manager);

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