package com.devtrack.tasks.domain.repository;

import com.devtrack.tasks.domain.entity.TaskEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskRepository extends MongoRepository<TaskEntity, String> {
}
