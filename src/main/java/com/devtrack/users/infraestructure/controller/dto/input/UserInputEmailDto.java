package com.devtrack.users.infraestructure.controller.dto.input;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInputEmailDto {
    @Email(message = "Email format not valid")
    private String email;
}
