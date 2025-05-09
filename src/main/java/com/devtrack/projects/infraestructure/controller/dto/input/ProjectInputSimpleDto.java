package com.devtrack.projects.infraestructure.controller.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectInputSimpleDto {
    @NotNull(message = "The field 'projectName' is required")
    private String projectName;
    @NotNull(message = "The field 'clientName' is required")
    private String clientName;
    @NotNull(message = "The field 'description' is required")
    private String description;
}
