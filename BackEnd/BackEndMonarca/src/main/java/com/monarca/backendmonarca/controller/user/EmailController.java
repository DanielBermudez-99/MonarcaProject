package com.monarca.backendmonarca.controller.user;

import com.monarca.backendmonarca.infra.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    EmailService emailService;

    @GetMapping("/sendEmail")
    public ResponseEntity<String> sendPasswordResetEmail(String to, String token) {
        emailService.sendPasswordResetEmail();
        return new ResponseEntity<>("Correo enviado con exito", HttpStatus.OK);
    }


}

