package com.monarca.backendmonarca.infra.services;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendPasswordResetEmail(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("andresitofonseca13@gmail.com");
        message.setTo("andresitofonseca13@gmail.com");
        message.setSubject("Muy cerdas XXX");
        message.setText("Ingresa al siguiente link para ver porno");
        javaMailSender.send(message);
    }
}
