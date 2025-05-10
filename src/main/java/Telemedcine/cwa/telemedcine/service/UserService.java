package Telemedcine.cwa.telemedcine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Telemedcine.cwa.telemedcine.dto.RegisterRequest;
import Telemedcine.cwa.telemedcine.dto.UserUpdateDTO;
import Telemedcine.cwa.telemedcine.model.Medecin;
import Telemedcine.cwa.telemedcine.model.Patient;
import Telemedcine.cwa.telemedcine.model.Role;
import Telemedcine.cwa.telemedcine.model.User;
import Telemedcine.cwa.telemedcine.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository ;
    @Autowired
    private UserRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User updateProfile(String jwtToken, UserUpdateDTO updateDTO) {
        return null;
        // ... (déjà implémenté)
    }

    public User registerUser(RegisterRequest request) {

        // Vérification si l'email est déjà utilisé
        if (utilisateurRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email déjà utilisé !");
        }

        // Encodage du mot de passe
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user;

        if (null == request.getRole()) {
            
            user = new Patient();

            user.setnom(request.getnom());
            user.setPrenom(request.getPrenom()); // ou demander dans le formulaire
            user.setEmail(request.getEmail());
            user.setPassword(encodedPassword);
            user.setRole(request.getRole());
        } 
        else switch (request.getRole()) {
            case MEDECIN -> {
                // Création d'un médecin
                Medecin medecin = new Medecin();
                medecin.setnom(request.getnom());
                medecin.setPrenom(request.getPrenom()); // ou demander dans le formulaire
                medecin.setEmail(request.getEmail());
                medecin.setPassword(encodedPassword);
                medecin.setRole(Role.MEDECIN);
                medecin.setSpecialite(request.getSpecialite());
                user = medecin;
            }
          
                
            

            default -> {
                user = new Patient();
                user.setnom(request.getnom());
                user.setPrenom(request.getPrenom()); // ou demander dans le formulaire
                user.setEmail(request.getEmail());
                user.setPassword(encodedPassword);
                user.setRole(request.getRole());
            }
        }

        // Sauvegarde dans la base
        return utilisateurRepository.save(user);
    }
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email));
    }
    public User findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + id));
    }
    public void save(User user) {
        userRepository.save(user);
    }

}
