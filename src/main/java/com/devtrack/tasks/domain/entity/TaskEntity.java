package com.devtrack.tasks.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tasks")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskEntity {
    @Id
    @Builder.Default
    private String _id = new ObjectId().toString();
}
