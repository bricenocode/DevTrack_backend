package com.devtrack.projects.infraestructure.controller.dto.output;

import com.devtrack.tasks.infraestructure.controller.dto.output.TaskOutputSimpleDto;
import com.devtrack.users.infraestructure.controller.dto.output.UserOutputSimpleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectOutputFullDto {
    private String _id;
    private String projectName;
    private String clientName;
    private String description;
    private UserOutputSimpleDto manager;
    private List<TaskOutputSimpleDto> tasks;
    private List<UserOutputSimpleDto> team;
}
