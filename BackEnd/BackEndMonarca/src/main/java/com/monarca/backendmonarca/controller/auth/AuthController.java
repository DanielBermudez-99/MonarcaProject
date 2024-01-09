package com.monarca.backendmonarca.controller.auth;

import com.monarca.backendmonarca.domain.user.User;
import com.monarca.backendmonarca.domain.user.UserRepository;
import com.monarca.backendmonarca.infra.services.dto.LoginDto;
import com.monarca.backendmonarca.security.config.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = new JwtUtil();
    }


    // En AuthController.java

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(login);

        // Busca al usuario en la base de datos
        User user = userRepository.findByUsername(loginDto.getUsername());

        // Obtiene el id del usuario
        Long userId = user.getId();

        // Pasa el nombre de usuario y el id del usuario al método create
        String jwt = this.jwtUtil.create(loginDto.getUsername(), userId.toString());

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}