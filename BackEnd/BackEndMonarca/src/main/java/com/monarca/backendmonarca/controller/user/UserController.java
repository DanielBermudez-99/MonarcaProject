package com.monarca.backendmonarca.controller.user;

import com.monarca.backendmonarca.domain.user.DataRegisterUser;
import com.monarca.backendmonarca.domain.user.DataUpdateUser;
import com.monarca.backendmonarca.domain.user.User;
import com.monarca.backendmonarca.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserRepository UserRepository;

    @PostMapping
    public ResponseEntity<User> crearUsuario(@RequestBody DataRegisterUser dataRegisterUser) {
        User newUser = UserRepository.save(new User(dataRegisterUser));
        return ResponseEntity.ok(newUser);
    }

    @GetMapping
    public ResponseEntity<?> obtenerUsuarios(){
        return ResponseEntity.ok(UserRepository.findAll());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> actualizarUsuario(@PathVariable Integer id, @RequestBody DataUpdateUser dataUpdateUser){
        User user = UserRepository.getReferenceById(id);
        user.dataUpdate(dataUpdateUser);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> eliminarUsuario(@PathVariable Integer id){
        UserRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
