package com.devtrack.auth.domain.repository;

import com.devtrack.auth.domain.entity.TokenEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TokenRepository extends MongoRepository<TokenEntity, String> {
    Optional<TokenEntity> findByToken(String token);
}
