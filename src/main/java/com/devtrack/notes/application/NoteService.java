package com.devtrack.notes.application;

import com.devtrack.notes.infraestructure.controller.dto.input.NoteInputSimpleDto;
import com.devtrack.notes.infraestructure.controller.dto.output.NoteOutputFullDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NoteService {
    ResponseEntity<String> createNote(String projectId,String taskId, String userId, NoteInputSimpleDto noteInputSimpleDto);
    ResponseEntity<List<NoteOutputFullDto>> getTaskNotes(String projectId, String taskId);
    ResponseEntity<String> deleteNote(String projectId,String taskId,String noteId);
}
