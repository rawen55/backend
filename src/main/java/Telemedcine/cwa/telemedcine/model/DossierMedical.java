package Telemedcine.cwa.telemedcine.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class DossierMedical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String historique;
    @ManyToOne
    @JoinColumn(name = "medecin_id")
    private Medecin medecin;
    
    @OneToOne
    @JoinColumn(name = "patient_id", unique = true)
    private Patient patient;

    public DossierMedical() {}

    public DossierMedical(String historique, Patient patient) {
        this.historique = historique;
        this.patient = patient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHistorique() {
        return historique;
    }

    public void setHistorique(String historique) {
        this.historique = historique;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Medecin getMedecin() {
        return medecin;
    }

    public void setMedecin(Medecin medecin) {
        this.medecin = medecin;
    }
}
