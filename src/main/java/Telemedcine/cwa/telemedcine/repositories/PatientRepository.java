package Telemedcine.cwa.telemedcine.repositories;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Telemedcine.cwa.telemedcine.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    // Ici tu peux ajouter d'autres méthodes personnalisées si nécessaire
}

