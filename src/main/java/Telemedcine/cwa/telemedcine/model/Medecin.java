package Telemedcine.cwa.telemedcine.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
@DiscriminatorValue("MEDECIN")
public class Medecin extends User {

    @Column(nullable = false)
    private String specialite;

    @Column(nullable = false)
    private String numeroOrdre; 

    @OneToMany(mappedBy = "medecin", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RendezVous> rendezVousList;

    @OneToMany(mappedBy = "medecin", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DossierMedical> dossiersMedicaux;

   
    public Medecin(String nom, String email, String prenom, String password, Role role, String specialite, String numeroOrdre) {
        super(nom, prenom, email, password, role);
        this.specialite = specialite;
        this.numeroOrdre = numeroOrdre;
    }

    public Medecin() {}

   
    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getNumeroOrdre() {
        return numeroOrdre;
    }

    public void setNumeroOrdre(String numeroOrdre) {
        this.numeroOrdre = numeroOrdre;
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
}
