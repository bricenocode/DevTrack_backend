package com.devtrack.notes.domain.repository;

import com.devtrack.notes.domain.entity.NoteEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NoteRepository extends MongoRepository<NoteEntity, String> {
}
