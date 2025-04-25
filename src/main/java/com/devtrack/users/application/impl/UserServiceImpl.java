package com.devtrack.users.application.impl;

import com.devtrack.projects.domain.entity.ProjectEntity;
import com.devtrack.projects.domain.repository.ProjectRepository;
import com.devtrack.users.application.UserService;
import com.devtrack.users.application.mapper.input.UserInputMapper;
import com.devtrack.users.application.mapper.output.UserOutputMapper;
import com.devtrack.users.domain.entity.UserEntity;
import com.devtrack.users.domain.repository.UserRepository;
import com.devtrack.users.infraestructure.controller.dto.output.UserOutputSimpleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserInputMapper userInputMapper;
    private final UserOutputMapper userOutputMapper;
    private final ProjectRepository projectRepository;

    @Override
    public ResponseEntity<UserOutputSimpleDto> findMemberByEmail(String email) {
        System.out.println(email);
        UserEntity userEntity = userRepository.findUserEntitiesByEmail(email)
                .orElseThrow(() -> new RuntimeException(
                        "User with email " + email + " not found"));
        return ResponseEntity.status(HttpStatus.OK)
                .body(userOutputMapper.entityToOutputSimpleDto(userEntity));
    }

    @Override
    public ResponseEntity<String> addMemberById(String memberId, String projectId) {

        UserEntity member = this.userRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException(
                        "User with id " + memberId + " not found"));

        ProjectEntity project = this.projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException(
                        "Project with id " + projectId + " not found"));

        project.getTeam().forEach(team -> {
            if(team.get_id().equals(memberId))
                throw new RuntimeException(
                        "User with id " + memberId + " already exists on team");
        });
        project.getTeam().add(member);
        this.projectRepository.save(project);
        return ResponseEntity.status(HttpStatus.CREATED).body("User added successfully");
    }

    @Override
    public ResponseEntity<String> removeMemberById(String memberId, String projectId) {

        UserEntity member = this.userRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException(
                        "User with id " + memberId + " not found"));

        ProjectEntity project = this.projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException(
                        "Project with id " + projectId + " not found"));

        if(!project.getTeam().contains(member))
            throw new RuntimeException("User with id " + memberId + " does not exist on team");

        project.getTeam().remove(member);
        this.projectRepository.save(project);
        return ResponseEntity.status(HttpStatus.CREATED).body("User removed successfully");
    }

    @Override
    public ResponseEntity<List<UserOutputSimpleDto>> getProjectTeam(String id) {
        ProjectEntity project = this.projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Project with id " + id + " not found"));
        List<UserOutputSimpleDto> team = project.getTeam()
                .stream()
                .map(this.userOutputMapper::entityToOutputSimpleDto)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(team);
    }
}
