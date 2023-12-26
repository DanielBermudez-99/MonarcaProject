package com.monarca.backendmonarca.controller.email;

import com.monarca.backendmonarca.infra.services.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/password-reset")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping("/password-reset")
    public ResponseEntity<?> passwordReset(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        if (email == null) {
            return new ResponseEntity<>("Email is required", HttpStatus.BAD_REQUEST);
        }
        passwordResetService.createPasswordResetTokenForUser(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/reset-password")
    public void resetPassword(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        String newPassword = body.get("password");
        passwordResetService.resetPassword(token, newPassword);
    }
}
