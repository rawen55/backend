package Telemedcine.cwa.telemedcine.contoller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import Telemedcine.cwa.telemedcine.config.UserDetailsImpl;
import Telemedcine.cwa.telemedcine.model.User;
import Telemedcine.cwa.telemedcine.service.UserService;

@RestController
@RequestMapping("api/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
@Autowired
private UserService userService;
@GetMapping("/me")
public ResponseEntity<User> getCurrentUser(@RequestParam("patientId") Long patientId) {
    User user = userService.findById(patientId);
    if (user == null) {
        return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(user);
}
    
@GetMapping("/profile")
public ResponseEntity<User> getUserProfile(@RequestParam("patientId") Long patientId) {
    User user = userService.findById(patientId); // Fetch the user by ID
    if (user == null) {
        return ResponseEntity.notFound().build(); // Return 404 if user not found
    }
    return ResponseEntity.ok(user); // Return the user details
}

@PostMapping("/{id}/photo/upload")
public ResponseEntity<User> uploadPhoto(
        @PathVariable Long id,
        @RequestParam("file") MultipartFile file,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

    System.out.println("Authenticated user ID: " + (userDetails != null ? userDetails.getId() : "null"));
    System.out.println("Requested user ID: " + id);
    
    try {
        // Verify if the user is authenticated and authorized
        if (userDetails == null || !userDetails.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Save the file to a local directory
        String uploadDir = System.getProperty("user.dir") + "/uploads/";
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.write(filePath, file.getBytes());

        String imageUrl = "http://localhost:8080/uploads/" + fileName;


        // Update the user's profile with the new photo URL
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        user.setPhotoUrl(imageUrl);
        userService.save(user);

        // Return the updated user object
        return ResponseEntity.ok(user);

    } catch (IOException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}}