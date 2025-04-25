package com.devtrack.auth.infraestructure.controller;

import com.devtrack.auth.application.TokenService;
import com.devtrack.auth.infraestructure.controller.dto.input.TokenInputNameDto;
import com.devtrack.users.application.mapper.input.UserInputMapper;
import com.devtrack.users.infraestructure.controller.dto.input.UserInputLoginDto;
import com.devtrack.users.infraestructure.controller.dto.input.UserInputSimpleDto;
import com.devtrack.users.infraestructure.controller.dto.output.UserOutputSimpleDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;
    private final UserInputMapper userInputMapper;

    @PostMapping("/create-account")
    public ResponseEntity<String> createAccount(
            @RequestBody
            @Valid
            UserInputSimpleDto userInputSimpleDto
    ){
        return tokenService.save(userInputMapper.inputSimpleDtoToEntity(userInputSimpleDto));
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody
            @Valid
            UserInputLoginDto userInputLoginDto
    ){
        return tokenService.login(userInputMapper.inputLoginDtoToEntity(userInputLoginDto));
    }


    @PostMapping("/confirm-account")
    public ResponseEntity<String> confirmAccount(
            @RequestBody
            TokenInputNameDto token
    ){
        return tokenService.confirmAccount(token.getToken());
    }

    @PostMapping("/request-code")
    public ResponseEntity<String> requestConfirmationCode(
            @RequestBody
            String email
    ){
        return tokenService.requestConfirmationCode(email);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(
            @RequestBody
            String email
    ){
        return tokenService.forgotPassword(email);
    }

    @PostMapping("/validate-password")
    public ResponseEntity<String> validatePassword(
            @RequestBody
            TokenInputNameDto token
    ){
        return tokenService.validateToken(token.getToken());
    }

    @PutMapping("/update-password")
    public ResponseEntity<String> updatePasswordWithToken(
            @RequestBody
            String password,
            @RequestBody
            String passwordConfirmation
    ){
        if(!password.equals(passwordConfirmation)){
            throw new RuntimeException("Passwords do not match");
        }
        return tokenService.updatePasswordWithToken(password);
    }

    @GetMapping("/user")
    public ResponseEntity<UserOutputSimpleDto> user(){
        return tokenService.user();
    }

}
