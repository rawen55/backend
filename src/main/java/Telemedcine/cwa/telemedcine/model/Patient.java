package Telemedcine.cwa.telemedcine.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
@DiscriminatorValue("PATIENT")
public class Patient extends User {

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
}
