package Telemedcine.cwa.telemedcine.service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Telemedcine.cwa.telemedcine.dto.RendezVousDTO;
import Telemedcine.cwa.telemedcine.model.Medecin;
import Telemedcine.cwa.telemedcine.model.Patient;
import Telemedcine.cwa.telemedcine.model.RendezVous;
import Telemedcine.cwa.telemedcine.model.StatutRdv;
import Telemedcine.cwa.telemedcine.repositories.MedecinRepository;
import Telemedcine.cwa.telemedcine.repositories.PatientRepository;
import Telemedcine.cwa.telemedcine.repositories.RendezVousRepository;


@Service
public class RendezVousService {

    @Autowired
    private final RendezVousRepository rendezVousRepository;

    @Autowired
    private MedecinRepository medecinRepository;

    @Autowired
    private PatientRepository patientRepository;

    public RendezVous createRendezVous(RendezVousDTO dto) {
        Medecin medecin = medecinRepository.findById(dto.getMedecinId())
            .orElseThrow(() -> new RuntimeException("Médecin non trouvé"));

        Patient patient = patientRepository.findById(dto.getPatientId())
            .orElseThrow(() -> new RuntimeException("Patient non trouvé"));

        RendezVous rv = new RendezVous();
        rv.setMedecin(medecin);
        rv.setPatient(patient);
        rv.setDate(dto.getDate());
        rv.setDocument(dto.getDocument());
        try {
            rv.setStatutrdv(StatutRdv.valueOf("EN_ATTENTE"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Statut du rendez-vous invalide");
        }
        return rendezVousRepository.save(rv);
        
    
    }
    public RendezVous accepterRendezVous(Long id) throws Exception {
        RendezVous rendezVous = rendezVousRepository.findById(id)
            .orElseThrow(() -> new Exception("Rendez-vous non trouvé"));
        rendezVous.setStatutrdv(StatutRdv.ACCEPTE);
        return rendezVousRepository.save(rendezVous);
    }
    public RendezVousService(RendezVousRepository rendezVousRepository) {
        this.rendezVousRepository = rendezVousRepository;
    }
    /**
     * Sauvegarde ou met à jour un objet RendezVous.
     * Nécessaire pour service.save(rdv).
     */
    public RendezVous findById(Long id) {
        Optional<RendezVous> rdv = rendezVousRepository.findById(id);
        return rdv.orElse(null);
    }

    public void save(RendezVous rendezVous) {
        rendezVousRepository.save(rendezVous);
    }

    /**
     * Récupère tous les rendez-vous pour un médecin donné et un statut précis.
     * Nécessaire pour service.getByMedecinAndStatut(...).
     */
  
     public List<RendezVous> getRendezVousByMedecinId(Long medecinId) {
        return rendezVousRepository.findByMedecinId(medecinId);  // Assure-toi que cette méthode retourne une liste de rendez-vous.
    }
    public List<RendezVous> getAll() {
        return rendezVousRepository.findAll();
    }
    public List<RendezVous> getRendezVousByPatientId(Long patientId) {
        return rendezVousRepository.findByPatientId(patientId);
    }
    public void supprimerRendezVous(Long id) throws Exception {
        RendezVous rendezVous = rendezVousRepository.findById(id)
            .orElseThrow(() -> new Exception("Rendez-vous non trouvé"));
        rendezVousRepository.delete(rendezVous);
 
    }
    

    public Map<String, Object> getWeeklyStats() {
        return calculateStats("WEEK");
    }

    public Map<String, Object> getMonthlyStats() {
        return calculateStats("MONTH");
    }

    public Map<String, Object> getYearlyStats() {
        return calculateStats("YEAR");
    }

    private Map<String, Object> calculateStats(String period) {
        List<Object[]> stats = switch (period) {
            case "WEEK" -> rendezVousRepository.findWeeklyStats();
            case "MONTH" -> rendezVousRepository.findMonthlyStats();
            case "YEAR" -> rendezVousRepository.findYearlyStats();
            default -> throw new IllegalArgumentException("Invalid period: " + period);
        };

        Map<String, Object> result = new HashMap<>();
        List<String> labels = new ArrayList<>();
        List<Long> acceptedCounts = new ArrayList<>();
        List<Long> totalPatients = new ArrayList<>();

        for (Object[] stat : stats) {
            labels.add(stat[0].toString()); // e.g., week number, month name, or year
            acceptedCounts.add((Long) stat[1]); // Count of accepted consultations
            totalPatients.add((Long) stat[2]); // Count of unique patients
        }

        result.put("labels", labels);
        result.put("accepted", acceptedCounts);
        result.put("totalPatients", totalPatients);
        return result;
    }


    public Long countNewConsultationsForMedecin(Long medecinId) {
        // Count consultations with status EN_ATTENTE for the given doctor
        return rendezVousRepository.countByMedecinIdAndStatutrdv(medecinId, StatutRdv.EN_ATTENTE);
    }

    public RendezVous reportRendezVous(Long id, LocalDateTime newDate) throws Exception {
    RendezVous rendezVous = rendezVousRepository.findById(id)
        .orElseThrow(() -> new Exception("Rendez-vous non trouvé"));
    rendezVous.setDate(newDate);
    rendezVous.setStatutrdv(StatutRdv.REPORTE); // Assuming "REPORTE" is a valid status in your `StatutRdv` enum
    return rendezVousRepository.save(rendezVous);
}



}