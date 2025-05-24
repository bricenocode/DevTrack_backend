package com.devtrack.users.application;

import com.devtrack.users.domain.entity.UserEntity;
import com.devtrack.users.infraestructure.controller.dto.output.UserOutputSimpleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<UserEntity> findUsersByEmail(String query);
    ResponseEntity<UserOutputSimpleDto> findMemberByEmail(String email);
    ResponseEntity<String> addMemberById(String memberId, String projectId);
    ResponseEntity<String> removeMemberById(String memberId, String projectId);
    ResponseEntity<List<UserOutputSimpleDto>>  getProjectTeam(String id);
}
