package com.devtrack.users.infraestructure.controller.dto.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInputLoginDto {
    @Email(message = "Email format not valid")
    private String email;
    @NotNull(message = "The field 'password' is required")
    private String password;
}
