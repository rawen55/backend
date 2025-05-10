package Telemedcine.cwa.telemedcine.repositories;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import Telemedcine.cwa.telemedcine.model.RendezVous;
import Telemedcine.cwa.telemedcine.model.StatutRdv;

public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {
    List<RendezVous> findByMedecinId(Long medecinId);
  

    // Corriger ici : utiliser statutrdv au lieu de statutRdv
    List<RendezVous> findByMedecinIdAndStatutrdv(Long medecinId, StatutRdv statutrdv);

    List<RendezVous> findByPatientId(Long patientId);

    List<RendezVous> findByMedecinIdAndDateBetween(Long medecinId, LocalDate start, LocalDate end);

    @Query("SELECT TO_CHAR(r.date, 'YYYY-IW') AS week, " +
           "SUM(CASE WHEN r.statutrdv = 'ACCEPTE' THEN 1 ELSE 0 END) AS accepted, " +
           "SUM(CASE WHEN r.statutrdv = 'REPORTE' THEN 1 ELSE 0 END) AS rescheduled, " +
           "SUM(CASE WHEN r.statutrdv = 'REFUSE' THEN 1 ELSE 0 END) AS refused " +
           "FROM RendezVous r " +
           "GROUP BY week " +
           "ORDER BY week ASC")
    List<Object[]> findWeeklyStats();

}