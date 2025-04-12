package Telemedcine.cwa.telemedcine.contoller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Telemedcine.cwa.telemedcine.model.RendezVous;
import Telemedcine.cwa.telemedcine.model.StatutRdv;
import Telemedcine.cwa.telemedcine.service.RendezVousService;

@RestController
@RequestMapping("/api/rendezvous")
public class RendezVousController {

    private final RendezVousService rendezVousService;

    public RendezVousController(RendezVousService rendezVousService) {
        this.rendezVousService = rendezVousService;
    }

    @PreAuthorize("hasAuthority('PATIENT')")
    @PostMapping("/demander")
    public ResponseEntity<String> demanderRdv(@RequestBody RendezVous rdv) {
        rendezVousService.demanderRdv(rdv);
        return ResponseEntity.ok("Demande de rendez-vous envoyée");
    }

    @PreAuthorize("hasAuthority('MEDECIN')")
    @GetMapping("/medecin/{medecinId}")
    public ResponseEntity<List<RendezVous>> voirDemandes(@PathVariable Long medecinId) {
        return ResponseEntity.ok(rendezVousService.voirDemandes(medecinId).stream().toList());
    }

    @PreAuthorize("hasAuthority('MEDECIN')")
    @PatchMapping("/{id}/statut")
    public ResponseEntity<RendezVous> modifierStatut(@PathVariable Long id, @RequestParam StatutRdv statut) {
        return ResponseEntity.ok(rendezVousService.modifierStatut(id, statut));
    }

    @PreAuthorize("hasAuthority('MEDECIN')")
    @PostMapping("/rapport/{id}")
    public ResponseEntity<RendezVous> redigerRapport(@PathVariable Long id, @RequestBody String rapport) {
        return ResponseEntity.ok(rendezVousService.redigerRapport(id, rapport));
    }
}
