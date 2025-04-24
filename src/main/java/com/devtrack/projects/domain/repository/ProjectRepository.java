package com.devtrack.projects.domain.repository;

import com.devtrack.projects.domain.entity.ProjectEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends MongoRepository<ProjectEntity, String> {
}
