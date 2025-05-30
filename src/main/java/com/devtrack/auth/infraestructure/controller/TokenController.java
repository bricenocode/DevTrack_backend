package com.devtrack.auth.infraestructure.controller;

import com.devtrack.auth.application.TokenService;
import com.devtrack.auth.application.mapper.input.TokenInputMapper;
import com.devtrack.auth.infraestructure.controller.dto.input.*;
import com.devtrack.users.application.mapper.input.UserInputMapper;
import com.devtrack.users.infraestructure.controller.dto.input.UserInputEmailDto;
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
    private final TokenInputMapper tokenInputMapper;

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
            UserInputEmailDto userInputEmailDto
    ){
        return tokenService.forgotPassword(userInputEmailDto.getEmail());
    }

    @PostMapping("/validate-password")
    public ResponseEntity<String> validatePassword(
            @RequestBody
            TokenInputDto tokenInputDto
    ){
        return tokenService.validateToken(tokenInputDto.getToken());
    }

    @PutMapping("/update-password/{token}")
    public ResponseEntity<String> updatePasswordWithToken(
            @PathVariable
            String token,
            @RequestBody
            TokenInputChangePasswordDto tokenInputChangePasswordDto

    ){
        if(!tokenInputChangePasswordDto.getPassword()
                .equals(tokenInputChangePasswordDto.getPasswordConfirmation())){
            throw new RuntimeException("Passwords do not match");
        }
        return tokenService.updatePasswordWithToken(token, tokenInputChangePasswordDto.getPassword());
    }

    @PutMapping("/update-password")
    public ResponseEntity<String> updatePassword(
            @RequestBody
            TokenInputChangePasswordDto tokenInputChangePasswordDto

    ){
        if(!tokenInputChangePasswordDto.getPassword()
                .equals(tokenInputChangePasswordDto.getPasswordConfirmation())){
            throw new RuntimeException("Passwords do not match");
        }
        return tokenService.updatePassword(tokenInputChangePasswordDto.getPassword());
    }

    @GetMapping("/user")
    public ResponseEntity<UserOutputSimpleDto> user(){
        return tokenService.user();
    }

    @PutMapping("/profile")
    public ResponseEntity<String> profile(
            @RequestBody
            TokenInputProfileDto tokenInputProfileDto) throws Exception {
        return this.tokenService.updateProfile(tokenInputMapper.inputProfileDtoToUserEntity(tokenInputProfileDto));
    }

    @PostMapping("/check-password")
    public ResponseEntity<String> checkPassword(
            @RequestBody
            TokenInputPasswordDto passwordDto
    ){
        return tokenService.checkPassword(passwordDto.getPassword());
    }
}
