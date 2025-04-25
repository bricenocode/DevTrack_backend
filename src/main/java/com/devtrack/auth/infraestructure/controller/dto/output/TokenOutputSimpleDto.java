package com.devtrack.auth.infraestructure.controller.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenOutputSimpleDto {
    private String _id;
    private String token;
    private String userId;
    private String expiresAt;
}
