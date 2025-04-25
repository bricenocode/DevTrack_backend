package com.devtrack.tasks.infraestructure.controller.dto.output;


import com.devtrack.completedby.infraestructure.controller.dto.output.CompletedByOutputDto;
import com.devtrack.notes.infraestructure.controller.dto.output.NoteOutputFullDto;
import com.devtrack.notes.infraestructure.controller.dto.output.NoteOutputSimpleDto;
import com.devtrack.projects.infraestructure.controller.dto.output.ProjectOutputSimpleDto;
import com.devtrack.utils.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskOutputFullDto {
    private String _id;
    private String name;
    private String description;
    private TaskStatus status;
    private Instant createdAt;
    private Instant updatedAt;
    private String project;
    private List<CompletedByOutputDto> completedBy;
    private List<NoteOutputFullDto> notes;
}
