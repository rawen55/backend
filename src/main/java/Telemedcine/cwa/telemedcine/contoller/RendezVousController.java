package Telemedcine.cwa.telemedcine.contoller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import Telemedcine.cwa.telemedcine.model.Medecin;
import Telemedcine.cwa.telemedcine.model.Patient;
import Telemedcine.cwa.telemedcine.model.RendezVous;
import Telemedcine.cwa.telemedcine.model.StatutRdv;
import Telemedcine.cwa.telemedcine.repositories.MedecinRepository;
import Telemedcine.cwa.telemedcine.repositories.PatientRepository;
import Telemedcine.cwa.telemedcine.repositories.RendezVousRepository;
import Telemedcine.cwa.telemedcine.service.RendezVousService;

@RestController
@RequestMapping("/api/rendezvous")
@CrossOrigin(origins = "http://localhost:4200") // Autorise Angular à accéder au backend
public class RendezVousController {

    @Autowired
    private MedecinRepository medecinRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private RendezVousRepository rendezVousRepository;
    // Création d’un rendez-vous
   @PostMapping("/create")
public ResponseEntity<?> createRendezVous(
        @RequestParam("date") String dateStr,
        @RequestParam("description") String description,
        @RequestParam("medecinId") Long medecinId,
        @RequestParam("patientId") Long patientId,
        @RequestParam(value = "document", required = false) MultipartFile document
) {
    try {
        LocalDateTime date = LocalDateTime.parse(dateStr);

        Medecin medecin = medecinRepository.findById(medecinId)
                .orElseThrow(() -> new RuntimeException("Médecin non trouvé"));
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient non trouvé"));

        RendezVous rendezVous = new RendezVous();
        rendezVous.setDate(date);
        rendezVous.setDescription(description);
        rendezVous.setMedecin(medecin);
        rendezVous.setPatient(patient);
        rendezVous.setStatutrdv(StatutRdv.EN_ATTENTE);
        if (document != null && !document.isEmpty()) {
            System.out.println("Nom du fichier reçu : " + document.getOriginalFilename());
        
            // ✅ Chemin de destination corrigé
            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            File uploadFolder = new File(uploadDir);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }
        
            String filename = System.currentTimeMillis() + "_" + document.getOriginalFilename();
            File file = new File(uploadDir + filename);
        
            // ✅ Enregistrement du fichier
            document.transferTo(file);
        
            // Stocker le chemin (optionnel : enregistrer seulement le nom de fichier)
            rendezVous.setDocument(uploadDir + filename);
        }
    
            // Sauvegarde en base
            RendezVous saved = rendezVousRepository.save(rendezVous);
            return ResponseEntity.ok(saved);
    
        } catch (IOException | RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur lors de la création : " + e.getMessage());
        }
    }
    private static final Logger log = LoggerFactory.getLogger(RendezVousController.class);
   // @PreAuthorize("hasAuthority('MEDECIN')")//
   @PutMapping("/{id}/statut")
public ResponseEntity<?> accepterRendezVous(@PathVariable Long id) {
    try {
        RendezVous rendezVous = rendezVousService.accepterRendezVous(id);
        return ResponseEntity.ok(rendezVous);
    } catch (Exception e) {
        log.error("Erreur lors de l'acceptation du rendez-vous", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur serveur");
    }
}

    // 2) Le médecin récupère ses rendez-vous
   @Autowired
   private RendezVousService rendezVousService;
   @GetMapping("/medecin/me")
   public ResponseEntity<List<RendezVous>> getMesRendezVous() {
       // Récupère l'email de l'utilisateur authentifié
       String email = SecurityContextHolder.getContext().getAuthentication().getName();
       
       // Cherche le médecin par son email
       Medecin medecin = medecinRepository.findByUserEmail(email)
           .orElseThrow(() -> new RuntimeException("Médecin introuvable"));
   
       // Récupère les rendez-vous associés à ce médecin
       List<RendezVous> rendezVousList = rendezVousService.getRendezVousByMedecinId(medecin.getId());
       
       // Retourne la liste des rendez-vous sous forme de réponse
       return ResponseEntity.ok(rendezVousList);
   }
   
 @DeleteMapping("/{id}")
public ResponseEntity<?> supprimerRendezVous(@PathVariable Long id) {
    try {
        rendezVousService.supprimerRendezVous(id);
        return ResponseEntity.ok("Rendez-vous supprimé avec succès");
    } catch (Exception e) {
        log.error("Erreur lors de la suppression du rendez-vous", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur serveur");
    }
}
@PreAuthorize("hasRole('MEDECIN')")
@GetMapping("/medecin/{id}")
public ResponseEntity<List<RendezVous>> getRendezVousByMedecin(@PathVariable Long id) {
    return ResponseEntity.ok(rendezVousService.getRendezVousByMedecinId(id));
}
    // 3) Le médecin reporte la date d’un RDV
    @PostMapping("/rendezvous/{id}/report")
    public ResponseEntity<Map<String, String>> reportRendezVous(
            @PathVariable Long id,
            @RequestBody Map<String, String> payload) {
        String newDate = payload.get("newDate");
        // Logique pour reporter le rendez-vous
        Map<String, String> response = Map.of(
            "message", "Rendez-vous reporté",
            "newDate", newDate
        );
        return ResponseEntity.ok(response);
    }
    @GetMapping("/patient/{id}")
    public ResponseEntity<List<RendezVous>> getRendezVousByPatient(@PathVariable Long id) {
        // Récupère les rendez-vous associés au patient
        List<RendezVous> rendezVousList = rendezVousService.getRendezVousByPatientId(id);
        return ResponseEntity.ok(rendezVousList);
    }
   

  

    @GetMapping("/stats/weekly/{medecinId}")
 
    public ResponseEntity<?> getWeeklyStatsForMedecin(@PathVariable Long medecinId) {
        Map<String, Object> stats = rendezVousService.getWeeklyStatsForMedecin(medecinId);
        return ResponseEntity.ok(stats);
    }

}