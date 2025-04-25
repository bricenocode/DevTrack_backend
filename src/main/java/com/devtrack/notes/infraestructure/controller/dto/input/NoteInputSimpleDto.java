package com.devtrack.notes.infraestructure.controller.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoteInputSimpleDto {
    @NotNull(message = "The field 'content' is required")
    private String content;
}
