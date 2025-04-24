package com.devtrack.projects.domain.entity;

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

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "projects")
public class ProjectEntity {

    @Id
    @Builder.Default
    private String _id = new ObjectId().toString();
    private String projectName;
    private String clientName;
    private String description;

    @DBRef(lazy = false)
    @Builder.Default
    private List<TaskEntity> tasks = new ArrayList<>();

    @DBRef(lazy = false)
    private UserEntity manager;

    @DBRef(lazy = false)
    @Builder.Default
    private List<UserEntity> team = new ArrayList<>();
}
