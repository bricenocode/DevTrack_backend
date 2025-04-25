package com.devtrack.projects.infraestructure.controller.dto.input;

import com.devtrack.users.infraestructure.controller.dto.input.UserInputSimpleDto;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectInputManagerDto {
    @NotNull(message = "The field 'projectName' is required")
    private String projectName;
    @NotNull(message = "The field 'clientName' is required")
    private String clientName;
    @NotNull(message = "The field 'description' is required")
    private String description;
    @NotNull(message = "The field 'manager' is required")
    private UserInputSimpleDto manager;
}
