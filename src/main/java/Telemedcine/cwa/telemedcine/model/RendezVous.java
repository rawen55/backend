package Telemedcine.cwa.telemedcine.model;

import java.time.LocalDateTime;
import java.util.Optional;

import jakarta.persistence.Column;
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

    public static void save(RendezVous rdv) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static Optional<RendezVous> findById(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private User patient;

    @ManyToOne
    @JoinColumn(name = "medecin_id")
    private User medecin;

    private LocalDateTime dateHeure;

    @Enumerated(EnumType.STRING)
    private StatutRdv statut = StatutRdv.EN_ATTENTE;

    @Column(columnDefinition = "TEXT")
    private String rapport;

  
    public RendezVous() {
    }

    public RendezVous(User patient, User medecin, LocalDateTime dateHeure, StatutRdv statut, String rapport) {
        this.patient = patient;
        this.medecin = medecin;
        this.dateHeure = dateHeure;
        this.statut = statut;
        this.rapport = rapport;
    }

   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getPatient() {
        return patient;
    }

    public void setPatient(User patient) {
        this.patient = patient;
    }

    public User getMedecin() {
        return medecin;
    }

    public void setMedecin(User medecin) {
        this.medecin = medecin;
    }

    public LocalDateTime getDateHeure() {
        return dateHeure;
    }

    public void setDateHeure(LocalDateTime dateHeure) {
        this.dateHeure = dateHeure;
    }

    public StatutRdv getStatut() {
        return statut;
    }

    public void setStatut(StatutRdv statut) {
        this.statut = statut;
    }

    public String getRapport() {
        return rapport;
    }

    public void setRapport(String rapport) {
        this.rapport = rapport;
    }

    @Override
    public String toString() {
        return "RendezVous{" +
                "id=" + id +
                ", patient=" + (patient != null ? patient.getId() : "null") +
                ", medecin=" + (medecin != null ? medecin.getId() : "null") +
                ", dateHeure=" + dateHeure +
                ", statut=" + statut +
                ", rapport='" + rapport + '\'' +
                '}';
    }
}

