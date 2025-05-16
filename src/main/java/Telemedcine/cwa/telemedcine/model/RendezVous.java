package Telemedcine.cwa.telemedcine.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "rendez_vous")
public class RendezVous {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;


    private String document; // on stocke le nom du fichier ici

    // Removed duplicate declaration of statutrdv

    @ManyToOne
    @JoinColumn(name = "medecin_id")
    private Medecin medecin;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
    
    @ElementCollection
    private List<String> documents; // Change from String document to List<String> documents

    private String dossierMedicalePath; // NEW


// Getters et setters pour les documents
    public List<String> getDocuments() { return documents; }

    public void setDocuments(List<String> documents) { this.documents = documents; }

 @Enumerated(EnumType.STRING)
@Column(name = "statut_rdv") // Mapper à la colonne statut_rdv
private StatutRdv statutrdv; // Enum pour le statut du rendez-vous

    public RendezVous() {}

    // getters et setters
    public Long getId() {
        return id;
    }
    
    public String getDossierMedicalePath() { return dossierMedicalePath; }
    public void setDossierMedicalePath(String dossierMedicalePath) { this.dossierMedicalePath = dossierMedicalePath; }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public Medecin getMedecin() {
        return medecin;
    }

    public void setMedecin(Medecin medecin) {
        this.medecin = medecin;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    
    public void setDiagnostic(String diagnostic) {
     
        throw new UnsupportedOperationException("Unimplemented method 'setDiagnostic'");
    }

    public StatutRdv getStatutrdv() {
        return statutrdv;
    }

    public void setStatutrdv(StatutRdv statutrdv) {
        this.statutrdv = statutrdv;
    }
}




