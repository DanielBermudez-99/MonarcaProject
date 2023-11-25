package com.monarca.backendmonarca.controller.user;

import com.monarca.backendmonarca.domain.user.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CrudRepositoryUser crudRepositoryUser;

    @PostMapping
    public ResponseEntity<User> crearUsuario(@RequestBody DataRegisterUser dataRegisterUser) {
        User newUser = userRepository.save(new User(dataRegisterUser));
        return ResponseEntity.ok(newUser);
    }

    @GetMapping
    public ResponseEntity<?> obtenerUsuarios(){

        return ResponseEntity.ok(userRepository.findAll());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody DataUpdateUser dataUpdateUser){
        User user = crudRepositoryUser.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.dataUpdate(dataUpdateUser);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id){
        crudRepositoryUser.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
