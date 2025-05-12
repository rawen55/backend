package Telemedcine.cwa.telemedcine.contoller;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Telemedcine.cwa.telemedcine.dto.MedecinDTO;
import Telemedcine.cwa.telemedcine.model.Role;
import Telemedcine.cwa.telemedcine.model.User;
import Telemedcine.cwa.telemedcine.repositories.MedecinRepository;
import Telemedcine.cwa.telemedcine.repositories.RendezVousRepository;
import Telemedcine.cwa.telemedcine.repositories.UserRepository;


@RestController
@RequestMapping("/api/medecin")
public class MedecinController {


    private final UserRepository userRepository;
    private final MedecinRepository medecinRepository;
    public MedecinController(UserRepository userRepository, MedecinRepository medecinRepository, RendezVousRepository rendezVousRepository) {
        this.userRepository = userRepository;
        this.medecinRepository = medecinRepository;
    }

   
    @GetMapping
    public List<MedecinDTO> getAllMedecins() {
        System.out.println(">>>> Appel API /api/medecin reçu !");
        List<User> users = userRepository.findByRole(Role.MEDECIN);
        System.out.println(">>>> Nombre de médecins trouvés : " + users.size());
    
        return users.stream()
            .map(user -> {
                Telemedcine.cwa.telemedcine.model.Medecin medecin = medecinRepository.findById(user.getId()).orElse(null);
                String specialite = (medecin != null) ? medecin.getSpecialite() : "";
                String adresse = (medecin != null) ? medecin.getAdresse() : "";
                // Créez un objet MedecinDTO avec les informations nécessaires
                return new MedecinDTO(
                    user.getId(),
                    user.getNom(),
                    user.getPrenom(),
                    user.getEmail(),
                    specialite,
                    adresse
                );
            })
            .collect(Collectors.toList());
    }
  

   

}