package com.devtrack.tasks.infraestructure.controller.dto.input;

import com.devtrack.utils.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskInputStatusDto {
    TaskStatus status;
}
