package Telemedcine.cwa.telemedcine.repositories;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Telemedcine.cwa.telemedcine.model.Medecin;



@Repository
public interface MedecinRepository extends JpaRepository<Medecin, Long> {
 public Medecin findByEmail(String email);
  
Optional<Medecin> findByUserEmail(String email);
}
  