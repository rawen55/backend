package Telemedcine.cwa.telemedcine.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String nom;

    @Column(name = "last_name")
    private String prenom;

    @Column(name = "email_id")
    private String email;

    @Column(name = "password") 
    private String password;

    @Enumerated(EnumType.STRING) 
    @Column(name = "role")
    private Role role;

    public  User() {}

    public User(String nom,String prenom, String email, String password, Role role) {
        this.nom = nom;
        this.prenom=prenom;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    public void sInscrire() {
    
    }

    public void seConnecter() {
     
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(Object nom) { this.nom = (String) nom; }

    public String getEmail() { return email; }
    public void setEmail(Object email) { this.email = (String) email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public Object map(Object object) {
       
        throw new UnsupportedOperationException("Unimplemented method 'map'");
    }

    public void setPrenom(Object prenom2) {
        
        throw new UnsupportedOperationException("Unimplemented method 'setPrenom'");
    }

    public String getPrenom() {
        return prenom;
    }

    
}
