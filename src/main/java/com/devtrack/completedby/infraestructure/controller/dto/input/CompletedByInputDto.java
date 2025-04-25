package com.devtrack.completedby.infraestructure.controller.dto.input;

import com.devtrack.utils.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompletedByInputDto {
    private TaskStatus status;
}
