package com.devtrack.auth.infraestructure.controller.dto.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenInputSimpleDto {
    private String token;
    private String userId;
}
