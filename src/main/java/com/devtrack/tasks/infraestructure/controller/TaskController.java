package com.devtrack.tasks.infraestructure.controller;


import com.devtrack.tasks.application.TaskService;
import com.devtrack.tasks.infraestructure.controller.dto.input.TaskInputSimpleDto;
import com.devtrack.tasks.infraestructure.controller.dto.input.TaskInputStatusDto;
import com.devtrack.tasks.infraestructure.controller.dto.output.TaskOutputFullDto;
import com.devtrack.tasks.infraestructure.controller.dto.output.TaskOutputSimpleDto;
import com.devtrack.utils.enums.TaskStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/{projectId}/tasks")
    public ResponseEntity<String> createTask(
            @PathVariable String projectId,
            @RequestBody TaskInputSimpleDto taskInput) {
        return this.taskService.createTask(projectId, taskInput);
    }

    @GetMapping("/{projectId}/tasks")
    public ResponseEntity<List<TaskOutputSimpleDto>> getProjectTask(
            @PathVariable
            String projectId
    ){
        return this.taskService.getProjectTasks(projectId);
    }

    @GetMapping("/{projectId}/tasks/{taskId}")
    public ResponseEntity<TaskOutputFullDto> getTaskById(
            @PathVariable
            String projectId,
            @PathVariable
            String taskId
    ){
        return this.taskService.getTaskById(projectId, taskId);
    }

    @PutMapping("/{projectId}/tasks/{taskId}")
    public ResponseEntity<String> updateTask(
            @PathVariable
            String projectId,
            @PathVariable
            String taskId,
            @RequestBody
            TaskInputSimpleDto taskInput
    ) {
        return this.taskService.updateTask(projectId, taskId, taskInput);
    }

    @DeleteMapping("/{projectId}/tasks/{taskId}")
    public ResponseEntity<String> removeTask(
            @PathVariable
            String projectId,
            @PathVariable
            String taskId){
     return this.taskService.removeTask(projectId, taskId);
    }

    @PostMapping(value = "/{projectId}/tasks/{taskId}/status")
    public ResponseEntity<String> updateStatus(
            @PathVariable
            String projectId,
            @PathVariable
            String taskId,
            @RequestBody
            TaskInputStatusDto taskInputStatusDto){
        System.out.println(taskInputStatusDto.getStatus());
        return this.taskService.updateStatus(projectId, taskId, taskInputStatusDto.getStatus());
    }
}
