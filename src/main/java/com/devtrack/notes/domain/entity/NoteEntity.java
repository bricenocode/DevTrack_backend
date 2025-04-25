package com.devtrack.notes.domain.entity;

import com.devtrack.tasks.domain.entity.TaskEntity;
import com.devtrack.users.domain.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "notes")
public class NoteEntity {
    @Id
    @Builder.Default
    private String _id = new ObjectId().toString();
    private String content;
    @DBRef
    private UserEntity createdBy;
    @DBRef
    private TaskEntity task;
}
