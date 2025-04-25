package com.devtrack.email.application.impl;

import com.devtrack.email.application.EmailService;
import com.devtrack.email.domain.EmailEntity;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String correo;
    @Value("${frontend.url}")
    private String url;

    @Override
    public ResponseEntity<String> sendConfirmationEmail(EmailEntity email) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            String subject = "DevTrack - Confirm your account";
            String html = """
                    <p>Hello %s, you have successfully created your account on DevTrack. We're almost done. You just need to confirm your account!</p>
                    <p>Please visit the following link:</p>
                    <a href="%s/auth/confirm-account">Confirm Account</a>
                    <p>Enter the following code: <b>%s</b></p>
                    <p>This token expires in 10 minutes.</p>
                    """.formatted(email.getName(),url, email.getToken());

            helper.setFrom(correo);
            helper.setTo(email.getEmail());
            helper.setSubject(subject);
            helper.setText(html, true); // true for HTML content

            mailSender.send(message);
            return ResponseEntity.ok("Confirmation email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error while sending confirmation email");
        }
    }

    @Override
    public ResponseEntity<String> sendPasswordResetToken(EmailEntity email) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            String subject = "DevTrack - Reset your password";
            String html = """
                <p>Hello %s, you have requested to reset your password on DevTrack.</p>
                <p>Please visit the following link:</p>
                <a href="%s/auth/new-password">Reset Password</a>
                <p>Enter the following code: <b>%s</b></p>
                <p>This token expires in 10 minutes.</p>
                """.formatted(email.getName(),url, email.getToken());

            helper.setFrom(correo);
            helper.setTo(email.getEmail());
            helper.setSubject(subject);
            helper.setText(html, true); // true for HTML content

            mailSender.send(message);
            return ResponseEntity.ok("Password reset email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error while sending password reset email");
        }
    }

}
