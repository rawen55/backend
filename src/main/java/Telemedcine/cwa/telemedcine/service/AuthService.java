package Telemedcine.cwa.telemedcine.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Telemedcine.cwa.telemedcine.config.JwtTokenUtil;
import Telemedcine.cwa.telemedcine.dto.AuthRequest;
import Telemedcine.cwa.telemedcine.dto.AuthResponse;
import Telemedcine.cwa.telemedcine.dto.RegisterRequest;
import Telemedcine.cwa.telemedcine.model.Role;
import Telemedcine.cwa.telemedcine.model.User;
import Telemedcine.cwa.telemedcine.repositories.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email déjà utilisé !");
        }

        User user = new User();
        user.setNom(request.getNom());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.valueOf(request.getRole().toUpperCase()));

        userRepository.save(user);

        String token = jwtTokenUtil.generateToken(user.getEmail());
        return new AuthResponse(token);
    }

    public AuthResponse login(AuthRequest request) {
        Optional<User> user = userRepository.findByEmail(request.getEmail());

        if (user.isEmpty() || !passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
            throw new RuntimeException("Email ou mot de passe incorrect !");
        }

        String token = jwtTokenUtil.generateToken(user.get().getEmail());
        return new AuthResponse(token);
    }
}
