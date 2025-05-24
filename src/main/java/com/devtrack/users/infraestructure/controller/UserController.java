package com.devtrack.users.infraestructure.controller;

import com.devtrack.users.application.UserService;
import com.devtrack.users.domain.entity.UserEntity;
import com.devtrack.users.infraestructure.controller.dto.input.UserInputEmailDto;
import com.devtrack.users.infraestructure.controller.dto.input.UserInputIdDto;
import com.devtrack.users.infraestructure.controller.dto.output.UserOutputSimpleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<String>> searchUsers(@RequestParam String query) {
        // Llama a tu servicio de aplicación para buscar usuarios
        List<UserEntity> users = userService.findUsersByEmail(query);

        // Mapea las entidades de usuario a solo sus emails
        List<String> userEmails = users.stream()
                .map(UserEntity::getEmail)
                .toList();

        return ResponseEntity.ok(userEmails);
    }

    //Connected with ProjectController
    @PostMapping(path = "/projects/{projectId}/team/find")
    ResponseEntity<UserOutputSimpleDto> findMemberByEmail(
            @PathVariable
            String projectId,
            @RequestBody
            UserInputEmailDto userInputEmailDto) {
        return this.userService.findMemberByEmail(userInputEmailDto.getEmail());
    }

    @PostMapping(path = "/projects/{projectId}/team")
    ResponseEntity<String> addMemberById(
            @PathVariable
            String projectId,
            @RequestBody
            UserInputIdDto userInputIdDto) {
        System.out.println(userInputIdDto.getId());
        return this.userService.addMemberById(userInputIdDto.getId(), projectId);
    }

    @DeleteMapping(path = "/projects/{projectId}/team/{userId}")
    ResponseEntity<String> removeMemberById(
            @PathVariable
            String projectId,
            @PathVariable
            String userId
    ){
        return this.userService.removeMemberById(userId, projectId);
    }

    @GetMapping(path = "/projects/{projectId}/team")
    ResponseEntity<List<UserOutputSimpleDto>> getProjectMembers(
            @PathVariable
            String projectId
    ){
        return this.userService.getProjectTeam(projectId);
    }

}
