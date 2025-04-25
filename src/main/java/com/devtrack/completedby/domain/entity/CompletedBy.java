package com.devtrack.completedby.domain.entity;

import com.devtrack.users.domain.entity.UserEntity;
import com.devtrack.utils.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "completedBy")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompletedBy {
    private String _id;
    private UserEntity user;
    private TaskStatus status;
}
