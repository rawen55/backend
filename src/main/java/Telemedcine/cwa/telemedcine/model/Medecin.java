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

    @Column(nullable = false) // Champ adresse avec contrainte NOT NULL
    private String adresse;

    @Column(nullable = false) // Champ spécialité avec contrainte NOT NULL
    private String specialite;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "medecin")
    @JsonIgnore
    private List<RendezVous> rendezVousList;

    @OneToMany(mappedBy = "medecin", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DossierMedical> dossiersMedicaux;

    // Constructeur avec paramètres
    public Medecin(String nom, String email, String prenom, String password, Role role, String specialite, String adresse) {
        super(nom, prenom, email, password, role);
        this.specialite = specialite;
        this.adresse = adresse;
    }

    // Constructeur par défaut
    public Medecin() {}

    // Getter et setter pour adresse
    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    // Getter et setter pour spécialité
    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    // Getter et setter pour rendez-vous
    public List<RendezVous> getRendezVousList() {
        return rendezVousList;
    }

    public void setRendezVousList(List<RendezVous> rendezVousList) {
        this.rendezVousList = rendezVousList;
    }

    // Getter et setter pour dossiers médicaux
    public List<DossierMedical> getDossiersMedicaux() {
        return dossiersMedicaux;
    }

    public void setDossiersMedicaux(List<DossierMedical> dossiersMedicaux) {
        this.dossiersMedicaux = dossiersMedicaux;
    }

    // Getter et setter pour user
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}