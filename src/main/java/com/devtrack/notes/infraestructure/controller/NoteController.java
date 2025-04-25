package com.devtrack.notes.infraestructure.controller;

import com.devtrack.notes.application.NoteService;
import com.devtrack.notes.infraestructure.controller.dto.input.NoteInputSimpleDto;
import com.devtrack.notes.infraestructure.controller.dto.output.NoteOutputFullDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping("/{projectId}/tasks/{taskId}/notes")
    public ResponseEntity<List<NoteOutputFullDto>> getTaskNotes(
            @PathVariable
            String projectId,
            @PathVariable   
            String taskId
    ){
        return this.noteService.getTaskNotes(projectId, taskId);
    }

    @PostMapping("/{projectId}/tasks/{taskId}/notes/{userId}")
    public ResponseEntity<String> createNote(
            @PathVariable
            String projectId,
            @PathVariable
            String taskId,
            @PathVariable
            String userId,
            @RequestBody
            NoteInputSimpleDto noteInputSimpleDto){
        return this.noteService.createNote(projectId, taskId, userId, noteInputSimpleDto);
    }

    @DeleteMapping("/{projectId}d/tasks/:taskId/notes/")
    public ResponseEntity<String> deleteNote(
            @PathVariable
            String projectId,
            @PathVariable
            String taskId,
            @PathVariable
            String noteId
    ){
        return this.noteService.deleteNote(projectId, taskId, noteId);
    }
}
