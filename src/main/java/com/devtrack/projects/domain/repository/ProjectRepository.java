package com.devtrack.projects.domain.repository;

import com.devtrack.projects.domain.entity.ProjectEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends MongoRepository<ProjectEntity, String> {
    @Query("{$or: [ " +
            "{ $and: [ { 'manager._id': ?0 }, { 'team._id': { $ne: ?0 } } ] }, " +
            "{ $and: [ { 'manager._id': { $ne: ?0 } }, { 'team._id': ?0 } ] } " +
            "]}")
    List<ProjectEntity> findProjectsWhereUserIsManagerOrTeam(String userId);
  }
