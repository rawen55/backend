package Telemedcine.cwa.telemedcine.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Telemedcine.cwa.telemedcine.model.Disponibilite;

@Repository
public interface DisponibiliteRepository extends JpaRepository<Disponibilite, Long> {
    List<Disponibilite> findByMedecinId(Long medecinId);
}

