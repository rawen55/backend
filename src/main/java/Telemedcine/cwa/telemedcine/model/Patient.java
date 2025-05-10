package Telemedcine.cwa.telemedcine.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity

//@DiscriminatorValue("PATIENT")//
public class Patient extends User {
@OneToMany(mappedBy = "patient")
@JsonIgnore
private List<RendezVous> rendezVousList;

    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private DossierMedical dossierMedical;

    public Patient() {
        super(); 
    }

    public Patient(String nom, String prenom, String email, Role role, String password) {
        super(nom, prenom, email, password, role);
    }

    public DossierMedical getDossierMedical() {
        return dossierMedical;
    }

    public void setDossierMedical(DossierMedical dossierMedical) {
        this.dossierMedical = dossierMedical;
    }

    public void chercherMedecin() {
     
    }

    public void demanderConsultation() {
      
    }

    public void recevoirNotification() {
      
    }

    public List<RendezVous> getRendezVousList() {
        return rendezVousList;
    }

    public void setRendezVousList(List<RendezVous> rendezVousList) {
        this.rendezVousList = rendezVousList;
    }
}
