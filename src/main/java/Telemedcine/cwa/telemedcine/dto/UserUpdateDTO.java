package Telemedcine.cwa.telemedcine.dto;

public class UserUpdateDTO {
    private String nom;
    private String prenom;
    private String email;
    private String specialite; 

    public UserUpdateDTO() {
    }

    
    public UserUpdateDTO(String nom, String prenom, String email, String specialite) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.specialite = specialite;
    }

  
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }
}
