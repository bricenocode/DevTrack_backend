package com.devtrack.users.domain.repository;

import com.devtrack.users.domain.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
    Optional<UserEntity> findUserEntitiesByEmail(String email);
    boolean existsByEmail(String email);
}
