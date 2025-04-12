package Telemedcine.cwa.telemedcine.contoller;


import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Telemedcine.cwa.telemedcine.model.User;
import Telemedcine.cwa.telemedcine.repositories.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    // Injection via le constructeur (meilleure pratique)
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ✅ 1. Récupérer tous les utilisateurs
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    // ✅ 2. Récupérer un utilisateur par son ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ 3. Mettre à jour un utilisateur
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setNom(updatedUser.getNom());
            user.setPrenom(updatedUser.getPrenom());
            user.setEmail(updatedUser.getEmail());
            userRepository.save(user);
            return ResponseEntity.ok(user);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ 4. Supprimer un utilisateur
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return userRepository.findById(id).map(user -> {
            userRepository.delete(user);
            return ResponseEntity.ok("Utilisateur supprimé avec succès");
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}

