package com.devtrack.users.domain.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
@Builder
public class UserEntity {
    @Id
    @Builder.Default
    private String _id = new ObjectId().toString();
    @NotBlank(message = "field name is required")
    private String name;
    @Indexed(unique = true)
    @NotBlank(message = "field email is required")
    private String email;
    @NotBlank(message = "field password is required")
    private String password;
    private Boolean confirmed = false;
}
