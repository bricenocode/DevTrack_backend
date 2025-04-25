package com.devtrack.completedby.domain.repository;

import com.devtrack.completedby.domain.entity.CompletedBy;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletedByRepository extends MongoRepository<CompletedBy, String> {
}
