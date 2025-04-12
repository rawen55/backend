package Telemedcine.cwa.telemedcine.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Telemedcine.cwa.telemedcine.model.RendezVous;

public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {
    List<RendezVous> findByMedecinId(Long medecinId);
}
