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
        if(userRepository.existsByEmail(userEntity.getEmail())){
            throw new RuntimeException("Email already exists!");
        }
        userEntity.setPassword(/*passwordEncoder.encode(*/userEntity.getPassword()/*)*/);

        if(!userEntity.getConfirmed()){
            createAndSendToken(userEntity, "confirmation");
        }
        userRepository.save(userEntity);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Cuenta creada, revisa tu email para confirmarla!");
    }

    @Override
    public ResponseEntity<String> login(UserEntity userEntity) {
        UserEntity user = userRepository.findUserEntitiesByEmail(userEntity.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        if (!user.getConfirmed()) {
            createAndSendToken(user, "confirmation");
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("El usuario no está confirmado, un nuevo email de confirmación ha sido enviado.");
        }

        System.out.println(user.getPassword());
        System.out.println(userEntity.getPassword());
        System.out.println(passwordEncoder.matches(user.getPassword(), userEntity.getPassword()));
        System.out.println(passwordEncoder.encode(userEntity.getPassword()));
        if (!user.getPassword().equals(userEntity.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Contraseña incorrecta");
        }

        String jwt = jwtUtil.generateToken(userEntity.getEmail());
        System.out.println(jwt);
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
                .orElseThrow(() -> new RuntimeException("Token no válido"));

        UserEntity userEntity = tokenEntity.getUser();

        // Verificar si el usuario ya está confirmado
        if (userEntity.getConfirmed()) {
            throw new RuntimeException("La cuenta ya está confirmada");
        }

        // Establecer el estado de confirmación
        userEntity.setConfirmed(true);

        // Eliminar el token de la base de datos
        tokenRepository.delete(tokenEntity);

        // Guardar el usuario con el nuevo estado
        userRepository.save(userEntity);

        return ResponseEntity.status(HttpStatus.OK)
                .body("Cuenta confirmada exitosamente!");
    }


    @Override
    public ResponseEntity<String> requestConfirmationCode(String email) {
        UserEntity userEntity = userRepository.findUserEntitiesByEmail(email)
                .orElseThrow(() -> new RuntimeException("El usuario no está registrado"));

        if (userEntity.getConfirmed()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El usuario ya está confirmado");
        }

        createAndSendToken(userEntity, "confirmation");
        return ResponseEntity.status(HttpStatus.OK)
                .body("Se ha enviado un nuevo token de confirmación a tu correo");
    }

    @Override
    public ResponseEntity<String> forgotPassword(String email) {
        UserEntity userEntity = userRepository.findUserEntitiesByEmail(email)
                .orElseThrow(() -> new RuntimeException("El usuario no está registrado"));

        createAndSendToken(userEntity, "password");
        return ResponseEntity.status(HttpStatus.OK)
                .body("Revisa tu correo para las instrucciones de restablecimiento de contraseña");
    }

    @Override
    public ResponseEntity<String> validateToken(String token) {
        TokenEntity tokenEntity = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token no válido"));

        return ResponseEntity.status(HttpStatus.OK)
                .body("Token válido, puedes restablecer tu contraseña");
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
                .body("Contraseña cambiada con éxito");
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