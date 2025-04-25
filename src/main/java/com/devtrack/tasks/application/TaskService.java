package com.devtrack.tasks.application;

import com.devtrack.tasks.infraestructure.controller.dto.input.TaskInputSimpleDto;
import com.devtrack.tasks.infraestructure.controller.dto.output.TaskOutputFullDto;
import com.devtrack.tasks.infraestructure.controller.dto.output.TaskOutputSimpleDto;
import com.devtrack.utils.enums.TaskStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {

    ResponseEntity<String> createTask(String projectId, TaskInputSimpleDto taskInput);
    ResponseEntity<TaskOutputFullDto> getTaskById(String projectId, String taskId);
    ResponseEntity<List<TaskOutputSimpleDto>> getProjectTasks(String projectId);
    ResponseEntity<String> updateTask(String projectId, String taskId, TaskInputSimpleDto taskInput);
    ResponseEntity<String> removeTask(String projectId, String taskId);
    ResponseEntity<String> updateStatus(String projectId, String taskId, TaskStatus status);


}
