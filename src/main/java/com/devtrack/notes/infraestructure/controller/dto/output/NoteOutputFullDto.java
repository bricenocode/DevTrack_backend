package com.devtrack.notes.infraestructure.controller.dto.output;

import com.devtrack.tasks.infraestructure.controller.dto.output.TaskOutputSimpleDto;
import com.devtrack.users.infraestructure.controller.dto.output.UserOutputSimpleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoteOutputFullDto {
    private String _id;
    private String content;
    private UserOutputSimpleDto createdBy;
    private TaskOutputSimpleDto task;
}
