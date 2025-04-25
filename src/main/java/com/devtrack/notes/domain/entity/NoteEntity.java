package com.devtrack.notes.domain.entity;

import com.devtrack.tasks.domain.entity.TaskEntity;
import com.devtrack.users.domain.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "notes")
@EqualsAndHashCode(exclude = "task")
@ToString(exclude = "task")

public class NoteEntity {
    @Id
    @Builder.Default
    private String _id = new ObjectId().toString();
    private String content;
    @DBRef
    private UserEntity createdBy;
    @DBRef
    private TaskEntity task;
    @Builder.Default
    private Instant createdAt = Instant.now();
    @Builder.Default
    private Instant updatedAt = Instant.now();
}
