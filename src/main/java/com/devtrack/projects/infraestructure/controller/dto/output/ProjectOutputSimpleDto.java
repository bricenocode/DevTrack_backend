package com.devtrack.projects.infraestructure.controller.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectOutputSimpleDto {
    private String _id;
    private String projectName;
    private String clientName;
    private String description;
}
