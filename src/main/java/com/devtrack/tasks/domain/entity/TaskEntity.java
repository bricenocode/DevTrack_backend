package com.devtrack.tasks.domain.entity;

import com.devtrack.completedby.domain.entity.CompletedBy;
import com.devtrack.notes.domain.entity.NoteEntity;
import com.devtrack.projects.domain.entity.ProjectEntity;
import com.devtrack.utils.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "tasks")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskEntity {

    @Id
    @Builder.Default
    private String _id = new ObjectId().toString();

    private String name;
    private String description;
    private TaskStatus status;

    @DBRef
    private ProjectEntity project;

    private List<CompletedBy> completedBy = new ArrayList<>();

    @DBRef
    private List<NoteEntity> notes = new ArrayList<>();
    private Instant createdAt;
    private Instant updatedAt;
}
