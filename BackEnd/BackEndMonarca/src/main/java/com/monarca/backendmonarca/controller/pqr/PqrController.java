package com.monarca.backendmonarca.controller.pqr;


import com.monarca.backendmonarca.domain.pqr.DataRegisterPqr;
import com.monarca.backendmonarca.domain.pqr.DataUpdatePqr;
import com.monarca.backendmonarca.domain.pqr.Pqr;
import com.monarca.backendmonarca.domain.pqr.PqrRepository;
import com.monarca.backendmonarca.domain.user.User;
import com.monarca.backendmonarca.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pqr")
@CrossOrigin
public class PqrController {

    @Autowired
    private PqrRepository pqrRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<Pqr> crearPqr(@RequestBody DataRegisterPqr dataRegisterPqr) {
        Pqr newPqr = pqrRepository.save(new Pqr(dataRegisterPqr));
        return ResponseEntity.ok(newPqr);
    }

    @GetMapping("/list")
    public ResponseEntity<?> obtenerPqrs(){
        return ResponseEntity.ok(pqrRepository.findAll());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> actualizarPqr(@PathVariable Long id, @RequestBody DataUpdatePqr dataUpdatePqr){
        Pqr pqr = pqrRepository.findById(id).orElseThrow(() -> new RuntimeException("Pqr not found"));
        pqr.dataUpdate(dataUpdatePqr);
        pqrRepository.save(pqr);  // Guarda los cambios en la base de datos
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register/{userId}")
    public ResponseEntity<Pqr> crearPqr(@PathVariable Long userId, @RequestBody DataRegisterPqr dataRegisterPqr) {
        User user = userRepository.findById(String.valueOf(userId)).orElseThrow(() -> new RuntimeException("User not found"));
        Pqr newPqr = new Pqr(dataRegisterPqr);
        newPqr.setUser(user);
        pqrRepository.save(newPqr);
        return ResponseEntity.ok(newPqr);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> eliminarPqr(@PathVariable Long id){
        pqrRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
