package Telemedcine.cwa.telemedcine.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Telemedcine.cwa.telemedcine.dto.UserUpdateDTO;
import Telemedcine.cwa.telemedcine.model.Medecin;
import Telemedcine.cwa.telemedcine.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private JwtService jwtService;  

    @Autowired
    private UserRepository utilisateurRepository;

    public Telemedcine.cwa.telemedcine.model.User updateProfile(String jwtToken, UserUpdateDTO updateDTO) {
      
        Long userId = jwtService.getUserIdFromJwt(jwtToken);

        Telemedcine.cwa.telemedcine.model.User user = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé !"));

       
        if (updateDTO.getNom() != null) user.setNom(updateDTO.getNom());
        if (updateDTO.getPrenom() != null) user.setPrenom(updateDTO.getPrenom());
        if (updateDTO.getEmail() != null) user.setEmail(updateDTO.getEmail());

        
        if (user instanceof Medecin && updateDTO.getSpecialite() != null) {
            ((Medecin) user).setSpecialite(updateDTO.getSpecialite());
        }

        
        return utilisateurRepository.save(user);
    }
}
