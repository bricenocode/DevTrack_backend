package com.devtrack.completedby.domain.entity;

import com.devtrack.users.domain.entity.UserEntity;
import com.devtrack.utils.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompletedBy {
    private UserEntity user;
    private TaskStatus status;
}
