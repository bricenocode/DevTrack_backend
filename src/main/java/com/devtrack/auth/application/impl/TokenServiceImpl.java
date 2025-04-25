package com.devtrack.auth.application.impl;

import com.devtrack.auth.application.TokenService;
import com.devtrack.auth.application.mapper.output.TokenOutputMapper;
import com.devtrack.auth.domain.entity.TokenEntity;
import com.devtrack.auth.domain.repository.TokenRepository;
import com.devtrack.auth.infraestructure.controller.dto.output.TokenOutputSimpleDto;
import com.devtrack.email.application.EmailService;
import com.devtrack.email.domain.EmailEntity;
import com.devtrack.users.application.mapper.output.UserOutputMapper;
import com.devtrack.users.domain.entity.UserEntity;
import com.devtrack.users.domain.repository.UserRepository;
import com.devtrack.users.infraestructure.controller.dto.output.UserOutputSimpleDto;
import com.devtrack.utils.JwtUtil;
import com.devtrack.utils.OtpGenerator;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final TokenOutputMapper tokenOutputMapper;
    private final UserOutputMapper userOutputMapper;
    private final PasswordEncoder passwordEncoder;
    private final OtpGenerator otpGenerator;
    private final JwtUtil jwtUtil;

    private static final long TOKEN_EXPIRATION_MILLIS = 10 * 60 * 1000;

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostConstruct
    public void init() {
        mongoTemplate.indexOps(TokenEntity.class)
                .ensureIndex(new Index().on("expiresAt", Sort.Direction.ASC).expire(0));
    }

    @Override
    public ResponseEntity<TokenOutputSimpleDto> findByToken(String tokenId) {
        TokenEntity tokenEntity = tokenRepository.findById(tokenId)
                .orElseThrow(() -> new RuntimeException("Token not found!"));
        return ResponseEntity.status(HttpStatus.OK)
                .body(tokenOutputMapper.entityToOutputSimpleDto(tokenEntity));
    }

    @Override
    public ResponseEntity<String> save(UserEntity userEntity) {
        if (userRepository.existsByEmail(userEntity.getEmail())) {
            throw new RuntimeException("Email already exists!");
        }
        userEntity.setPassword(userEntity.getPassword());

        if (!userEntity.getConfirmed()) {
            createAndSendToken(userEntity, "confirmation");
        }
        userRepository.save(userEntity);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Account created, check your email to confirm!");
    }

    @Override
    public ResponseEntity<String> login(UserEntity userEntity) {
        UserEntity user = userRepository.findUserEntitiesByEmail(userEntity.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!user.getConfirmed()) {
            createAndSendToken(user, "confirmation");
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("The user is not confirmed, a new confirmation email has been sent.");
        }

        if (!passwordEncoder.matches(userEntity.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Incorrect password");
        }

        String jwt = jwtUtil.generateToken(userEntity.getEmail());
        return ResponseEntity.status(HttpStatus.OK)
                .body(jwt);
    }

    @Override
    public ResponseEntity<String> delete(String tokenId) {
        TokenEntity tokenEntity = tokenRepository.findById(tokenId)
                .orElseThrow(() -> new RuntimeException("Token not found!"));
        tokenRepository.delete(tokenEntity);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Token deleted!");
    }

    @Transactional
    public ResponseEntity<String> confirmAccount(String token) {
        TokenEntity tokenEntity = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        UserEntity userEntity = tokenEntity.getUser();

        if (userEntity.getConfirmed()) {
            throw new RuntimeException("Account already confirmed");
        }

        userEntity.setConfirmed(true);
        tokenRepository.delete(tokenEntity);
        userRepository.save(userEntity);

        return ResponseEntity.status(HttpStatus.OK)
                .body("Account successfully confirmed!");
    }

    @Override
    public ResponseEntity<String> requestConfirmationCode(String email) {
        UserEntity userEntity = userRepository.findUserEntitiesByEmail(email)
                .orElseThrow(() -> new RuntimeException("User is not registered"));

        if (userEntity.getConfirmed()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User is already confirmed");
        }

        createAndSendToken(userEntity, "confirmation");
        return ResponseEntity.status(HttpStatus.OK)
                .body("A new confirmation token has been sent to your email");
    }

    @Override
    public ResponseEntity<String> forgotPassword(String email) {
        UserEntity userEntity = userRepository.findUserEntitiesByEmail(email)
                .orElseThrow(() -> new RuntimeException("User is not registered"));

        createAndSendToken(userEntity, "password");
        return ResponseEntity.status(HttpStatus.OK)
                .body("Check your email for password reset instructions");
    }

    @Override
    public ResponseEntity<String> validateToken(String password) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserEntity userEntity = userRepository.findUserEntitiesByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!userEntity.getPassword().equals(password)) {
             throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Wrong password!");
        }
        userEntity.setPassword(password);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Password correct!");
    }

    @Override
    public ResponseEntity<String> updatePasswordWithToken(String newPassword) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserEntity userEntity = userRepository.findUserEntitiesByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userEntity.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(userEntity);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Password successfully changed");
    }

    @Override
    public ResponseEntity<UserOutputSimpleDto> user() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserEntity userEntity = userRepository.findUserEntitiesByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return ResponseEntity.status(200)
                .body(userOutputMapper.entityToOutputSimpleDto(userEntity));
    }

    private void createAndSendToken(UserEntity userEntity, String emailType) {
        String token = otpGenerator.generateToken();
        Date expirationDate = new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_MILLIS);

        TokenEntity tokenEntity = TokenEntity.builder()
                .user(userEntity)
                .token(token)
                .expiresAt(expirationDate)
                .build();
        tokenRepository.save(tokenEntity);

        EmailEntity emailEntity = new EmailEntity(userEntity.getEmail(), userEntity.getName(), token);

        switch (emailType) {
            case "confirmation" -> emailService.sendConfirmationEmail(emailEntity);
            case "password" -> emailService.sendPasswordResetToken(emailEntity);
            default -> throw new IllegalArgumentException("Unknown email type");
        }
    }
}
