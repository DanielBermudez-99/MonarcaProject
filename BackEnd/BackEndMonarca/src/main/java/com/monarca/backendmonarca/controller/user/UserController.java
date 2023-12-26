package com.monarca.backendmonarca.controller.user;

import com.monarca.backendmonarca.domain.user.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CrudRepositoryUser crudRepositoryUser;

    //Inyectamos el password encoder para encriptar la contraseña

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<User> crearUsuario(@RequestBody DataRegisterUser dataRegisterUser) {
        User newUser = userRepository.save(new User(dataRegisterUser, passwordEncoder));
        return ResponseEntity.ok(newUser);
    }


    @GetMapping("/list")
    public ResponseEntity<?> obtenerUsuarios(){

        return ResponseEntity.ok(userRepository.findAll());
    }

    @PutMapping("update/{id}")
    @Transactional
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody DataUpdateUser dataUpdateUser){
        User user = crudRepositoryUser.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.dataUpdate(dataUpdateUser);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("delete/{id}")
    @Transactional
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id){
        crudRepositoryUser.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
