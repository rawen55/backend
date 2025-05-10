package Telemedcine.cwa.telemedcine.dto;

import java.time.LocalDateTime;

public class RendezVousDTO{
    private Long medecinId;
    private Long patientId;
    private String description;
    private LocalDateTime date;
    private String document; // peut être un nom, base64 ou null

    public void setMedecinId(Long medecinId) {
        this.medecinId = medecinId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getMedecinId() {
        return medecinId;
    }

    public String getStatutRdv() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}