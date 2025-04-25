package com.devtrack.email.application;

import com.devtrack.email.domain.EmailEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    ResponseEntity<String> sendConfirmationEmail(EmailEntity email);
    ResponseEntity<String> sendPasswordResetToken(EmailEntity email);
}
