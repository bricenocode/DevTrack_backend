package com.devtrack.projects.infraestructure.controller.dto.input;

import com.devtrack.tasks.infraestructure.controller.dto.input.TaskInputSimpleDto;
import com.devtrack.users.infraestructure.controller.dto.input.UserInputSimpleDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectInputFullDto {
    @NotNull(message = "The field 'projectName' is required")
    private String projectName;
    @NotNull(message = "The field 'clientName' is required")
    private String clientName;
    @NotNull(message = "The field 'description' is required")
    private String description;
    @NotNull(message = "The field 'tasks' is required")
    private List<TaskInputSimpleDto> tasks;
    @NotNull(message = "The field 'manager' is required")
    private UserInputSimpleDto manager;
    @NotNull(message = "The field 'team' is required")
    private List<UserInputSimpleDto> team;
}
