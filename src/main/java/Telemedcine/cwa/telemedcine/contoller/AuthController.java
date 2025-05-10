package Telemedcine.cwa.telemedcine.contoller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Telemedcine.cwa.telemedcine.config.JwtTokenUtil;
import Telemedcine.cwa.telemedcine.dto.AuthRequest;
import Telemedcine.cwa.telemedcine.dto.LoginResponse;
import Telemedcine.cwa.telemedcine.dto.RegisterRequest;
import Telemedcine.cwa.telemedcine.model.User;
import Telemedcine.cwa.telemedcine.repositories.UserRepository;
import Telemedcine.cwa.telemedcine.service.AuthService;
import Telemedcine.cwa.telemedcine.service.UserService;

@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthService authService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    @Autowired
    public AuthController(
        AuthService authService,
        JwtTokenUtil jwtTokenUtil,
        UserDetailsService userDetailsService,
        UserService userService
    ) {
        this.authService = authService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }
@Autowired
private UserRepository userRepository;
  @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
    authService.performAuthentication(authRequest.getEmail(), authRequest.getPassword());

    // Charger l'utilisateur complet pour récupérer son ID


        User user = userRepository.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));
    
        // Génère le token avec email en subject et userId en claim
        String token = jwtTokenUtil.generateToken(user);
    
        List<String> roles = List.of(user.getRole().name());
    
        return ResponseEntity.ok(new LoginResponse(token, roles));
    }
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
        User newUser = userService.registerUser(request);
        return ResponseEntity.ok(newUser);
    }
}