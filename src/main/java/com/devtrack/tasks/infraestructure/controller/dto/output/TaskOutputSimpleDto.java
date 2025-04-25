package com.devtrack.tasks.infraestructure.controller.dto.output;

import com.devtrack.utils.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskOutputSimpleDto {
    private String _id;
    private String name;
    private String description;
    private TaskStatus status;
    private Instant createdAt;
    private Instant updatedAt;
    /*private ProjectOutputSimpleDto project;
    private List<CompletedByOutputSimpleDto> completedBy;
    private List<NoteOutputSimpleDto> notes;*/

}
