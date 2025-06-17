package com.elitesoftwarehouse.corsiAParte.restcontroller;

import com.elitesoftwarehouse.corsiAParte.model.dto.UserCreateDTO;
import com.elitesoftwarehouse.corsiAParte.model.entity.User;
import com.elitesoftwarehouse.corsiAParte.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/nuovo")
    public ResponseEntity<User> createUser(@RequestBody UserCreateDTO userDTO) {
        try {
            // Verifica se l'utente esiste già nel database locale
            if (userService.existsByUsername(userDTO.getUsername())) {
                return ResponseEntity.badRequest().build();
            }
            
            // Converti il DTO in entità User
            User user = new User(
                userDTO.getUsername(),
                userDTO.getPassword(),
                userDTO.getEmail(),
                userDTO.getFirstName(),
                userDTO.getLastName()
            );
            
            User savedUser = userService.save(user);
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            // Verifica se l'utente esiste nel database locale prima di eliminarlo
            if (!userService.findByUsername(userService.findById(id).get().getUsername()).isPresent()) {
                return ResponseEntity.notFound().build();
            }
            
            userService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
