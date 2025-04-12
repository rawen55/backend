package Telemedcine.cwa.telemedcine.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import Telemedcine.cwa.telemedcine.model.RendezVous;
import Telemedcine.cwa.telemedcine.model.StatutRdv;
import Telemedcine.cwa.telemedcine.repositories.RendezVousRepository;

@Service
public class RendezVousService {

    private final RendezVousRepository rendezVousRepository;
    private final NotificationService notificationService;

    public RendezVousService(RendezVousRepository rendezVousRepository, NotificationService notificationService) {
        this.rendezVousRepository = rendezVousRepository;
        this.notificationService = notificationService;
    }

    public RendezVous demanderRdv(RendezVous rdv) {
        rdv.setStatut(StatutRdv.EN_ATTENTE);
        return rendezVousRepository.save(rdv);
    }

    public Optional<RendezVous> voirDemandes(Long medecinId) {
        return rendezVousRepository.findById(medecinId);
    }

    public RendezVous modifierStatut(Long id, StatutRdv statut) {
        return rendezVousRepository.findById(id)
                .map(rdv -> {
                    rdv.setStatut(statut);
                    rendezVousRepository.save(rdv);

                    // 🔔 Envoyer une notification au patient
                    String message = "Votre rendez-vous a été " + (statut == StatutRdv.ACCEPTE ? "accepté" : "refusé");
                    notificationService.notifierPatient(rdv.getPatient().getId(), message);

                    return rdv;
                }).orElseThrow(() -> new RuntimeException("Rendez-vous non trouvé"));
    }

    public RendezVous redigerRapport(Long id, String rapport) {
        return rendezVousRepository.findById(id)
                .map(rdv -> {
                    if (rdv.getStatut() != StatutRdv.ACCEPTE) {
                        throw new RuntimeException("Le RDV doit être accepté avant de rédiger un rapport");
                    }
                    rdv.setStatut(StatutRdv.TERMINE);
                    rdv.setRapport(rapport);
                    return rendezVousRepository.save(rdv);
                }).orElseThrow(() -> new RuntimeException("Rendez-vous non trouvé"));
    }
}
