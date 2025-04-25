package com.devtrack.users.infraestructure.controller;

import com.devtrack.users.application.UserService;
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

    //Connected with ProjectController
    @GetMapping(path = "/projects/{projectId}/team/find")
    ResponseEntity<UserOutputSimpleDto> findMemberByEmail(
            @PathVariable
            String projectId,
            @RequestBody
            String email){
        return this.userService.findMemberByEmail(email);
    }

    @PutMapping(path = "/projects/{projectId}/team")
    ResponseEntity<String> addMemberById(
            @PathVariable
            String projectId,
            @RequestBody
            String id ){
        return this.userService.addMemberById(id, projectId);
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
