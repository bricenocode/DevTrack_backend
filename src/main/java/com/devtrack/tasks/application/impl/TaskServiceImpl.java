package com.devtrack.tasks.application.impl;

import com.devtrack.completedby.domain.entity.CompletedBy;
import com.devtrack.completedby.domain.repository.CompletedByRepository;
import com.devtrack.projects.domain.entity.ProjectEntity;
import com.devtrack.projects.domain.repository.ProjectRepository;
import com.devtrack.tasks.application.TaskService;
import com.devtrack.tasks.application.mapper.input.TaskInputMapper;
import com.devtrack.tasks.application.mapper.output.TaskOutputMapper;
import com.devtrack.tasks.domain.entity.TaskEntity;
import com.devtrack.tasks.domain.repository.TaskRepository;
import com.devtrack.tasks.infraestructure.controller.dto.input.TaskInputSimpleDto;
import com.devtrack.tasks.infraestructure.controller.dto.output.TaskOutputFullDto;
import com.devtrack.tasks.infraestructure.controller.dto.output.TaskOutputSimpleDto;
import com.devtrack.users.domain.entity.UserEntity;
import com.devtrack.users.domain.repository.UserRepository;
import com.devtrack.utils.enums.TaskStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskInputMapper taskInputMapper;
    private final TaskOutputMapper taskOutputMapper;
    private final ProjectRepository projectRepository;
    private final CompletedByRepository completedByRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<String> createTask(String projectId, TaskInputSimpleDto taskInput) {

        TaskEntity taskEntity = this.taskInputMapper.inputSimpleDtoToEntity(taskInput);

        ProjectEntity projectEntity = this.projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException(
                        "Project not found"));
        
        taskEntity.setProject(projectEntity);
        TaskEntity taskSaved = taskRepository.save(taskEntity);
        projectEntity.getTasks().add(taskSaved);
        this.projectRepository.save(projectEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body("Task created!");
    }

    @Override
    public ResponseEntity<List<TaskOutputSimpleDto>> getProjectTasks(String projectId) {
        ProjectEntity projectEntity = this.projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException(
                        "Project not found"));
        return ResponseEntity.status(HttpStatus.OK)
                .body(projectEntity.getTasks()
                        .stream()
                        .map(this.taskOutputMapper::taskOutputSimpleDtoToEntity)
                        .toList());
    }

    @Override
    public ResponseEntity<TaskOutputFullDto> getTaskById(String projectId, String taskId) {
        ProjectEntity projectEntity = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException(
                        "Project not found"));
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException(
                        "Task not found"));
        if (!taskEntity.getProject().get_id().equals(projectEntity.get_id())) {
            throw new RuntimeException(
                    "Task does not belong to project"
            );
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(taskOutputMapper.taskOutputFullDtoToEntity(taskEntity));
    }

    @Override
    public ResponseEntity<String> updateTask(String projectId, String taskId, TaskInputSimpleDto taskInput) {
        ProjectEntity projectEntity = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException(
                        "Project not found"));

        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException(
                        "Task not found"));

        if (!taskEntity.getProject().get_id().equals(projectEntity.get_id())) {
            throw new RuntimeException(
                    "Task does not belong to project"
            );
        }
        Optional.ofNullable(taskInput.getName())
                .ifPresent(taskEntity::setName);
        Optional.ofNullable(taskInput.getDescription())
                .ifPresent(taskEntity::setDescription);
        Optional.ofNullable(taskInput.getStatus())
                .ifPresent(taskEntity::setStatus);
        taskEntity.setUpdatedAt(Instant.now());

        this.taskRepository.save(taskEntity);
        return ResponseEntity.status(HttpStatus.OK).body("Task updated!");
    }

    @Override
    public ResponseEntity<String> removeTask(String projectId, String taskId) {
        ProjectEntity projectEntity = this.projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException(
                        "Project not found"));
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException(
                        "Task not found"));


        if (!taskEntity.getProject().get_id().equals(projectEntity.get_id())) {
            throw new RuntimeException(
                    "Task does not belong to project"
            );
        }


        projectEntity.getTasks().remove(taskEntity);
        this.projectRepository.save(projectEntity);
        this.taskRepository.delete(taskEntity);
        return ResponseEntity.status(HttpStatus.OK).body("Task removed!");
    }

    @Override
    public ResponseEntity<String> updateStatus(String projectId, String taskId, TaskStatus status){
        ProjectEntity projectEntity = this.projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException(
                        "Project not found"));
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException(
                        "Task not found"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserEntity user = userRepository.findUserEntitiesByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!taskEntity.getProject().get_id().equals(projectEntity.get_id())) {
            throw new RuntimeException(
                    "Task does not belong to project"
            );
        }
        taskEntity.setUpdatedAt(Instant.now());
        taskEntity.setStatus(status);
        CompletedBy completedBy = CompletedBy.builder()
                .user(user)
                .status(status)
                .build();
        CompletedBy completedBySaved = completedByRepository.save(completedBy);
        taskEntity.getCompletedBy().add(completedBySaved);
        taskRepository.save(taskEntity);
        return ResponseEntity.status(HttpStatus.OK).body("Task status updated!");
    }
}
