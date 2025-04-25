package com.devtrack.email.domain;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailEntity {
    private String email;
    private String name;
    private String token;
}
