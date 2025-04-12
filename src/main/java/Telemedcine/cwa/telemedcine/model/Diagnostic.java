package Telemedcine.cwa.telemedcine.model;

import jakarta.persistence.*;

@Entity
public class Diagnostic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rendezvous_id", nullable = false)
    private RendezVous rendezVous;  

    private String diagnostic;
    
    @Column(columnDefinition = "TEXT")
    private String ordonnance;

    public Diagnostic() {}

    public Diagnostic(RendezVous rendezVous, String diagnostic, String ordonnance) {
        this.rendezVous = rendezVous;
        this.diagnostic = diagnostic;
        this.ordonnance = ordonnance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RendezVous getRendezVous() {
        return rendezVous;
    }

    public void setRendezVous(RendezVous rendezVous) {
        this.rendezVous = rendezVous;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public String getOrdonnance() {
        return ordonnance;
    }

    public void setOrdonnance(String ordonnance) {
        this.ordonnance = ordonnance;
    }
}
