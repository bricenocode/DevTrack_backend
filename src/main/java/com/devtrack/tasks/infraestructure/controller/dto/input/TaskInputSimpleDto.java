package com.devtrack.tasks.infraestructure.controller.dto.input;

import com.devtrack.completedby.infraestructure.controller.dto.input.CompletedByInputDto;
import com.devtrack.utils.enums.TaskStatus;
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
public class TaskInputSimpleDto {
    @NotNull(message = "The field 'name' is required")
    private String name;
    @NotNull(message = "The field 'description' is required")
    private String description;
    @NotNull(message = "The field 'status' is required")
    private TaskStatus status;
   @NotNull(message = "The field 'completedBy' is required")
    private List<CompletedByInputDto> completedBy;
}
