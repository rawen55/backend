package Telemedcine.cwa.telemedcine.contoller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
import Telemedcine.cwa.telemedcine.model.Notification;
import Telemedcine.cwa.telemedcine.model.Patient;
import Telemedcine.cwa.telemedcine.model.RendezVous;
import Telemedcine.cwa.telemedcine.model.StatutRdv;
import Telemedcine.cwa.telemedcine.repositories.MedecinRepository;
import Telemedcine.cwa.telemedcine.repositories.PatientRepository;
import Telemedcine.cwa.telemedcine.repositories.RendezVousRepository;
import Telemedcine.cwa.telemedcine.service.RendezVousService;

@RestController
@RequestMapping("/api/rendezvous")
@CrossOrigin(origins = "http://localhost:4200")
public class RendezVousController {

    @Autowired
    private MedecinRepository medecinRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private RendezVousRepository rendezVousRepository;

    @Autowired
    private RendezVousService rendezVousService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private static final Logger log = LoggerFactory.getLogger(RendezVousController.class);

    @PostMapping("/create")
    public ResponseEntity<?> createRendezVous(
        @RequestParam("date") String dateStr,
        @RequestParam("medecinId") Long medecinId,
        @RequestParam("patientId") Long patientId,
        @RequestParam(value = "documents", required = false) List<MultipartFile> documents,
        @RequestParam(value = "dossierMedicale", required = false) MultipartFile dossierMedicale
    ) {
        try {
            LocalDateTime date = LocalDateTime.parse(dateStr);

            Medecin medecin = medecinRepository.findById(medecinId)
                    .orElseThrow(() -> new RuntimeException("Médecin non trouvé"));
            Patient patient = patientRepository.findById(patientId)
                    .orElseThrow(() -> new RuntimeException("Patient non trouvé"));

            RendezVous rendezVous = new RendezVous();
            rendezVous.setDate(date);
            rendezVous.setMedecin(medecin);
            rendezVous.setPatient(patient);
            rendezVous.setStatutrdv(StatutRdv.EN_ATTENTE);

            List<String> documentPaths = new ArrayList<>();
            if (documents != null && !documents.isEmpty()) {
                String uploadDir = System.getProperty("user.dir") + "/uploads/";
                File uploadFolder = new File(uploadDir);
                if (!uploadFolder.exists()) {
                    uploadFolder.mkdirs();
                }
                for (MultipartFile doc : documents) {
                    if (!doc.isEmpty()) {
                        String filename = System.currentTimeMillis() + "_" + doc.getOriginalFilename();
                        File file = new File(uploadDir + filename);
                        doc.transferTo(file);
                        documentPaths.add(uploadDir + filename);
                    }
                }
            }
            rendezVous.setDocuments(documentPaths);

            if (dossierMedicale != null && !dossierMedicale.isEmpty()) {
                String uploadDir = System.getProperty("user.dir") + "/uploads/";
                File uploadFolder = new File(uploadDir);
                if (!uploadFolder.exists()) {
                    uploadFolder.mkdirs();
                }
                String dossierFilename = System.currentTimeMillis() + "_" + dossierMedicale.getOriginalFilename();
                File dossierFile = new File(uploadDir + dossierFilename);
                dossierMedicale.transferTo(dossierFile);
                rendezVous.setDossierMedicalePath(uploadDir + dossierFilename);
            }

            RendezVous saved = rendezVousRepository.save(rendezVous);
            return ResponseEntity.ok(saved);

        } catch (IOException | RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur lors de la création : " + e.getMessage());
        }
    }

    @PutMapping("/{id}/statut")
    public ResponseEntity<?> accepterRendezVous(@PathVariable Long id) {
        try {
            RendezVous rendezVous = rendezVousService.accepterRendezVous(id);
            messagingTemplate.convertAndSend(
                "/topic/notifications/" + rendezVous.getPatient().getId(),
                new Notification("Votre rendez-vous a été accepté.")
            );
            return ResponseEntity.ok(rendezVous);
        } catch (Exception e) {
            log.error("Erreur lors de l'acceptation du rendez-vous", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur serveur");
        }
    }

    @GetMapping("/medecin/me")
    public ResponseEntity<List<RendezVous>> getMesRendezVous() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Medecin medecin = medecinRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Médecin introuvable"));
        List<RendezVous> rendezVousList = rendezVousService.getRendezVousByMedecinId(medecin.getId());
        return ResponseEntity.ok(rendezVousList);
    }

    @GetMapping("/{rendezvousId}/dossier-medicale")
    public ResponseEntity<String> getDossierMedicaleByRendezVous(@PathVariable Long rendezvousId) {
        try {
            RendezVous rv = rendezVousRepository.findById(rendezvousId)
                .orElse(null);

            if (rv == null) {
                log.error("Aucun rendez-vous trouvé pour l'ID: {}", rendezvousId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rendez-vous non trouvé");
            }

            String pdfPath = rv.getDossierMedicalePath();
            if (pdfPath == null || pdfPath.trim().isEmpty()) {
                log.error("Aucun dossierMedicalePath pour le rendez-vous ID: {}", rv.getId());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun dossier médical trouvé");
            }

            String normalizedPath = pdfPath.replace("/", File.separator).replace("\\", File.separator);
            File file = new File(normalizedPath).getAbsoluteFile();
            log.info("Looking for dossier médicale at: {}", file.getAbsolutePath());
            if (!file.exists()) {
                log.error("Fichier dossier médicale introuvable à: {}", file.getAbsolutePath());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fichier non trouvé");
            }

            // Extract text from PDF using PDFBox
            try (PDDocument document = PDDocument.load(file)) {
                if (!document.isEncrypted()) {
                    PDFTextStripper stripper = new PDFTextStripper();
                    String text = stripper.getText(document);
                    return ResponseEntity.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(text);
                } else {
                    log.error("Le PDF est chiffré pour le rendez-vous ID: {}", rendezvousId);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le PDF est chiffré");
                }
            }

        } catch (IOException e) {
            log.error("Erreur lors de l'extraction du texte du dossier médical pour rendez-vous {}: {}", rendezvousId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'extraction du texte");
        } catch (Exception e) {
            log.error("Erreur inattendue pour rendez-vous {}: {}", rendezvousId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur serveur");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> supprimerRendezVous(@PathVariable Long id) {
        try {
            RendezVous rendezVous = rendezVousRepository.findById(id)
                .orElseThrow(() -> new Exception("Rendez-vous non trouvé"));
            rendezVous.setStatutrdv(StatutRdv.REFUSE);
            rendezVousRepository.save(rendezVous);
            return ResponseEntity.ok(Map.of("message", "Rendez-vous refusé avec succès"));
        } catch (Exception e) {
            log.error("Erreur lors du refus du rendez-vous", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Erreur serveur"));
        }
    }

    @PreAuthorize("hasRole('MEDECIN')")
    @GetMapping("/medecin/{id}")
    public ResponseEntity<List<RendezVous>> getRendezVousByMedecin(@PathVariable Long id) {
        return ResponseEntity.ok(rendezVousService.getRendezVousByMedecinId(id));
    }

    @PostMapping("/{id}/report")
    public ResponseEntity<?> reportRendezVous(@PathVariable Long id, @RequestBody Map<String, String> request) {
        try {
            String newDateStr = request.get("newDate");
            LocalDateTime newDate = LocalDateTime.parse(newDateStr);
            RendezVous updatedRendezVous = rendezVousService.reportRendezVous(id, newDate);
            return ResponseEntity.ok(updatedRendezVous);
        } catch (Exception e) {
            log.error("Erreur lors du report du rendez-vous", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur serveur");
        }
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<List<RendezVous>> getRendezVousByPatient(@PathVariable Long id) {
        List<RendezVous> rendezVousList = rendezVousService.getRendezVousByPatientId(id);
        return ResponseEntity.ok(rendezVousList);
    }

    @GetMapping("/medecin/new-consultations")
    public ResponseEntity<Long> getNewConsultationsForMedecin() {
        Long medecinId = getAuthenticatedMedecinId();
        Long newConsultationCount = rendezVousService.countNewConsultationsForMedecin(medecinId);
        return ResponseEntity.ok(newConsultationCount);
    }

    private Long getAuthenticatedMedecinId() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Medecin medecin = medecinRepository.findByEmail(email);
        if (medecin == null) {
            throw new RuntimeException("Médecin introuvable");
        }
        return medecin.getId();
    }

    @GetMapping("/stats/weekly")
    public ResponseEntity<Map<String, Object>> getWeeklyStats() {
        Map<String, Object> stats = rendezVousService.getWeeklyStats();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/stats/monthly")
    public ResponseEntity<Map<String, Object>> getMonthlyStats() {
        Map<String, Object> stats = rendezVousService.getMonthlyStats();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/stats/yearly")
    public ResponseEntity<Map<String, Object>> getYearlyStats() {
        Map<String, Object> stats = rendezVousService.getYearlyStats();
        return ResponseEntity.ok(stats);
    }
}