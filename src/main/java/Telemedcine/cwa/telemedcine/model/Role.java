package Telemedcine.cwa.telemedcine.model;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.persistence.Id;

public enum Role {
    PATIENT,
    MEDECIN,
    ADMIN;

    @JsonCreator
    public static Role from(String role) {
        return Role.valueOf(role.toUpperCase());
    }
    @Id
    private String name;
    @JsonValue
    public String getRole() {
        return this.name();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   
}

