package com.devtrack.auth.domain.entity;

import com.devtrack.users.domain.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
    
@Document(collection = "tokens")
public class TokenEntity {
    @Id
    @Builder.Default
    private String _id = new ObjectId().toString();
    private String token;
    @DBRef
    private UserEntity user;
    @Indexed(expireAfter = "0")
    private Date expiresAt;
}
