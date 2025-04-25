package com.devtrack.notes.infraestructure.controller.dto.output;

import com.devtrack.users.infraestructure.controller.dto.output.UserOutputSimpleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoteOutputFullDto {
    private String _id;
    private String content;
    private UserOutputSimpleDto createdBy;
    private String task;
    private Instant createdAt;
    private Instant updatedAt;
}
