package Telemedcine.cwa.telemedcine.model;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {
    public Admin() {}
    @Column(nullable = false)
    private String niveauAcces; 


    public Admin(String nom,String prenom, String email,Role role, String password, String niveauAcces) {
        super(nom,prenom ,email, password, role);
        this.niveauAcces = niveauAcces;
    }

    public String getNiveauAcces() { return niveauAcces; }
    public void setNiveauAcces(String niveauAcces) { this.niveauAcces = niveauAcces; }
    public void gererUtilisateurs() {
        
    }
}
