package com.devtrack.users.infraestructure.controller.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserOutputSimpleDto {
    private String _id;
    private String email;
    private String name;
    /*private Boolean confirmed;*/
}
