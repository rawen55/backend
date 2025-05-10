package Telemedcine.cwa.telemedcine.repositories;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Telemedcine.cwa.telemedcine.model.Medecin;



@Repository
public interface MedecinRepository extends JpaRepository<Medecin, Long> {

    Optional<Medecin> findByUserEmail(@Param("email") String email);
    

}
  