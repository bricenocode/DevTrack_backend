package com.devtrack.notes.application.impl;

import com.devtrack.notes.application.NoteService;
import com.devtrack.notes.application.mapper.input.NoteInputMapper;
import com.devtrack.notes.application.mapper.output.NoteOutputMapper;
import com.devtrack.notes.domain.entity.NoteEntity;
import com.devtrack.notes.domain.repository.NoteRepository;
import com.devtrack.notes.infraestructure.controller.dto.input.NoteInputSimpleDto;
import com.devtrack.notes.infraestructure.controller.dto.output.NoteOutputFullDto;
import com.devtrack.projects.domain.entity.ProjectEntity;
import com.devtrack.projects.domain.repository.ProjectRepository;
import com.devtrack.tasks.domain.entity.TaskEntity;
import com.devtrack.tasks.domain.repository.TaskRepository;
import com.devtrack.users.domain.entity.UserEntity;
import com.devtrack.users.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final NoteInputMapper noteInputMapper;
    private final NoteOutputMapper noteOutputMapper;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    @Override
    public ResponseEntity<String> createNote(
            String projectId,
            String taskId,
            NoteInputSimpleDto noteInputSimpleDto){
        ProjectEntity projectEntity = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException(
                        "Project not found"));
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException(
                "Task with id " + taskId + " not found"));
        if (!taskEntity.getProject().get_id().equals(projectEntity.get_id())) {
            throw new RuntimeException(
                    "Task does not belong to project"
            );
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserEntity userEntity = userRepository.findUserEntitiesByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        NoteEntity noteEntity = noteInputMapper.inputSimpleDtoToEntity(noteInputSimpleDto);

        noteEntity.setCreatedBy(userEntity);
        noteEntity.setTask(taskEntity);
        taskEntity.getNotes().add(noteEntity);

        noteRepository.save(noteEntity);
        taskRepository.save(taskEntity);

        return ResponseEntity.status(HttpStatus.OK)
                .body("Note created");
    }

    @Override
    public ResponseEntity<List<NoteOutputFullDto>> getTaskNotes(
            String projectId,
            String taskId) {
        ProjectEntity projectEntity = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException(
                        "Project not found"));
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException(
                        "Task with id " + taskId + " not found"));
        if (!taskEntity.getProject().get_id().equals(projectEntity.get_id())) {
            throw new RuntimeException(
                    "Task does not belong to project"
            );
        }
        List<NoteEntity> taskNotes = taskEntity.getNotes();
        return ResponseEntity.status(HttpStatus.OK)
                .body(taskNotes.stream()
                        .map(noteOutputMapper::entityToOutputFullDto)
                        .toList());
    }

    @Transactional
    @Override
    public ResponseEntity<String> deleteNote(
            String projectId,
            String taskId,
            String noteId) {
        ProjectEntity projectEntity = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task with id " + taskId + " not found"));

        if (!taskEntity.getProject().get_id().equals(projectEntity.get_id())) {
            throw new RuntimeException("Task does not belong to project");
        }

        NoteEntity noteEntity = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note with id " + noteId + " not found"));

        if (!taskEntity.getNotes().contains(noteEntity)) {
            throw new RuntimeException("Task does not belong to note");
        }

        taskEntity.getNotes().remove(noteEntity);
        noteRepository.delete(noteEntity);
        taskRepository.save(taskEntity);
        return ResponseEntity.status(HttpStatus.OK).body("Note deleted!");
    }

}
