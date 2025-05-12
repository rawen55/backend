package Telemedcine.cwa.telemedcine.dto;

public class MedecinDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String specialite;
    private String adresse;
    // Constructeur vide
    public MedecinDTO() {}

    // Constructeur avec tous les champs
    public MedecinDTO(Long id, String nom, String prenom, String email, String specialite,String adresse) {
        this.id = id;
        this.adresse = adresse;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.specialite = specialite;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getAdresse() {
        return adresse;
    }
}
