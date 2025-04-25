package com.devtrack.notes.infraestructure.controller.dto.input;

import com.devtrack.tasks.infraestructure.controller.dto.input.TaskInputSimpleDto;
import com.devtrack.users.infraestructure.controller.dto.input.UserInputSimpleDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoteInputFullDto {
    @NotNull(message = "The field 'content' is required")
    private String content;
    @NotNull(message = "The field 'createdBy' is required")
    private UserInputSimpleDto createdBy;
    @NotNull(message = "The field 'task' is required")
    private TaskInputSimpleDto task;
}
