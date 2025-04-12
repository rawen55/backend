package Telemedcine.cwa.telemedcine.contoller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Telemedcine.cwa.telemedcine.model.RendezVous;
import Telemedcine.cwa.telemedcine.model.StatutRdv;

@RestController
@RequestMapping("/api/medecin")
@PreAuthorize("hasAuthority('MEDECIN')")
public class MedecinController {

    @PostMapping("/accepter-rdv/{id}")
public ResponseEntity<String> accepterRdv(@PathVariable Long id) {
    if (id == null) {
        return ResponseEntity.badRequest().body("L'ID du rendez-vous est invalide");
    }

    Optional<RendezVous> optionalRdv = Optional.empty();
    if (!optionalRdv.isPresent()) {
        return ResponseEntity.badRequest().body("Rendez-vous introuvable");
    }

    RendezVous rdv = optionalRdv.get();
    rdv.setStatut(StatutRdv.ACCEPTE);
    RendezVous.save(rdv);

    return ResponseEntity.ok("RDV accepté");
}}
