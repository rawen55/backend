package Telemedcine.cwa.telemedcine.contoller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Telemedcine.cwa.telemedcine.model.Disponibilite;
import Telemedcine.cwa.telemedcine.service.DisponibiliteService;

@RestController
@RequestMapping("/api/disponibilites")
public class DisponibiliteController {
    @Autowired
    private DisponibiliteService disponibiliteService;

    // Récupérer les disponibilités d'un médecin
    @GetMapping("/{medecinId}")
    public List<Disponibilite> getDisponibilites(@PathVariable Long medecinId) {
        return disponibiliteService.getDisponibilitesMedecin(medecinId);
    }

    // Ajouter une nouvelle disponibilité
    @PostMapping
    public ResponseEntity<Disponibilite> ajouterDisponibilite(@RequestBody Disponibilite disponibilite) {
        Disponibilite nouvelleDispo = disponibiliteService.ajouterDisponibilite(disponibilite);
        return ResponseEntity.ok(nouvelleDispo);
    }
}