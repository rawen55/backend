package Telemedcine.cwa.telemedcine.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity

public class Medecin extends User {

    @Column(nullable = false)
    private String specialite;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
   @OneToMany(mappedBy = "medecin")
@JsonIgnore
private List<RendezVous> rendezVousList;

    
    @OneToMany(mappedBy = "medecin", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DossierMedical> dossiersMedicaux;
    
   
    public Medecin(String nom, String email, String prenom, String password, Role role, String specialite) {
        super(nom, prenom, email, password, role);
        this.specialite = specialite;
    }

    public Medecin() {}

   
    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }


    public List<RendezVous> getRendezVousList() {
        return rendezVousList;
    }

    public void setRendezVousList(List<RendezVous> rendezVousList) {
        this.rendezVousList = rendezVousList;
    }

    public List<DossierMedical> getDossiersMedicaux() {
        return dossiersMedicaux;
    }

    public void setDossiersMedicaux(List<DossierMedical> dossiersMedicaux) {
        this.dossiersMedicaux = dossiersMedicaux;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
