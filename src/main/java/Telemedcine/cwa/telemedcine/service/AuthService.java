package Telemedcine.cwa.telemedcine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Telemedcine.cwa.telemedcine.dto.RegisterRequest;
import Telemedcine.cwa.telemedcine.model.Medecin;
import Telemedcine.cwa.telemedcine.model.Patient;
import Telemedcine.cwa.telemedcine.model.Role;
import Telemedcine.cwa.telemedcine.model.User;
import Telemedcine.cwa.telemedcine.repositories.UserRepository;

@Service
public class AuthService {

    
        @Autowired
        private AuthenticationManager authenticationManager;
    
        public void performAuthentication(String email, String password) throws AuthenticationException {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
            );
        }
   

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

   
    public User registerUser(RegisterRequest request) {

        // Vérification si l'email est déjà utilisé
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email déjà utilisé !");
        }
        
    
        // Encodage du mot de passe
        String encodedPassword = passwordEncoder.encode(request.getPassword());
    
        User user;
    
        if (request.getRole() == Role.MEDECIN) {
            // Création d'un médecin
            Medecin medecin = new Medecin();
            medecin.setnom(request.getnom());
            medecin.setPrenom(request.getPrenom()); // récupérer depuis le formulaire
            medecin.setEmail(request.getEmail());
            medecin.setPassword(encodedPassword);
            medecin.setRole(Role.MEDECIN);
            medecin.setSpecialite(request.getSpecialite());
            user = medecin;
        } else {
            // Création d'un patient
            Patient patient = new Patient();
            patient.setnom(request.getnom());
            patient.setPrenom(request.getPrenom()); // récupérer depuis le formulaire
            patient.setEmail(request.getEmail());
            patient.setPassword(encodedPassword);
            patient.setRole(Role.PATIENT);
            user = patient;
        }
    
        // Sauvegarde dans la base
        return userRepository.save(user);
    }
}