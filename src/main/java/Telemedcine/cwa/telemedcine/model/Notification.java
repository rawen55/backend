package Telemedcine.cwa.telemedcine.model;

import java.time.LocalDateTime;

public class Notification {

    public Notification(String votre_rendezvous_a_été_accepté) {
    }
    private String message;
    private LocalDateTime date = LocalDateTime.now();

    // Constructeurs, getters, setters

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
