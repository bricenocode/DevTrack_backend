package com.devtrack.auth.application;

import com.devtrack.auth.infraestructure.controller.dto.output.TokenOutputSimpleDto;
import com.devtrack.users.domain.entity.UserEntity;
import com.devtrack.users.infraestructure.controller.dto.output.UserOutputSimpleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface TokenService {
    ResponseEntity<TokenOutputSimpleDto> findByToken(String tokenId);
    ResponseEntity<String> save(UserEntity userEntity);
    ResponseEntity<String> login(UserEntity userEntity);
    ResponseEntity<String> confirmAccount(String token);
    ResponseEntity<String> requestConfirmationCode(String email);
    ResponseEntity<String> forgotPassword(String email);
    ResponseEntity<String> validateToken(String token);
    ResponseEntity<String> updatePasswordWithToken(String token, String newPassword);
    ResponseEntity<UserOutputSimpleDto> user();
    ResponseEntity<String> updateProfile(UserEntity userEntity) throws Exception;
}
