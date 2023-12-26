package com.monarca.backendmonarca.infra.services;

import com.monarca.backendmonarca.domain.email.PasswordResetTokenRepository;
import com.monarca.backendmonarca.domain.email.PasswordResetToken;
import com.monarca.backendmonarca.domain.user.User;
import com.monarca.backendmonarca.domain.user.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void createPasswordResetTokenForUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        if (user != null) {
            String token = UUID.randomUUID().toString();
            PasswordResetToken passwordResetToken = new PasswordResetToken();
            passwordResetToken.setToken(token);
            passwordResetToken.setUser(user);
            passwordResetToken.setExpiryDate(LocalDateTime.now().plusHours(1));
            passwordResetTokenRepository.save(passwordResetToken);

            sendPasswordResetEmail(userEmail, token);
        }
    }

    private void sendPasswordResetEmail(String to, String token) {
        Context context = new Context();
        context.setVariable("token", token);
        String body = templateEngine.process("password-reset-template", context);

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("monarcainc3202@gmail.com");
            helper.setTo(to);
            helper.setSubject("Restablecer contraseña");
            helper.setText(body, true); // true indica que el correo es HTML
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            // manejar la excepción
        }
    }

    public void resetPassword(String token, String newPassword) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken != null && passwordResetToken.getExpiryDate().isAfter(LocalDateTime.now())) {
            User user = passwordResetToken.getUser();
            String encodedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedPassword);
            userRepository.save(user);
            passwordResetTokenRepository.delete(passwordResetToken);
        }
    }
}


