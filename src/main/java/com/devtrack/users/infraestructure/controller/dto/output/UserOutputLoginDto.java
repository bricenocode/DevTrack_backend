package com.devtrack.users.infraestructure.controller.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserOutputLoginDto {
    private String _id;
    private String name;
    private String email;
    private Boolean confirmed;
}
