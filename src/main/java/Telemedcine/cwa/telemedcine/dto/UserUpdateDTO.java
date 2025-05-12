package Telemedcine.cwa.telemedcine.dto;

public class UserUpdateDTO {
    private String nom;
    private String prenom;
    private String email;
    private String specialite; 
private String adresse;
    public UserUpdateDTO() {
    }

    
    public UserUpdateDTO(String nom, String prenom, String email, String specialite,String adresse) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.specialite = specialite;
        this.adresse = adresse;
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
}
