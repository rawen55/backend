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

    @Query("SELECT EXTRACT(WEEK FROM r.date) AS week, COUNT(r.id) AS acceptedCount, COUNT(DISTINCT r.patient.id) AS totalPatients " +
       "FROM RendezVous r " +
       "WHERE r.statutrdv = 'ACCEPTE' " +
       "GROUP BY EXTRACT(WEEK FROM r.date)")
    List<Object[]> findWeeklyStats();

    @Query("SELECT EXTRACT(MONTH FROM r.date) AS month, COUNT(r.id) AS acceptedCount, COUNT(DISTINCT r.patient.id) AS totalPatients " +
       "FROM RendezVous r " +
       "WHERE r.statutrdv = 'ACCEPTE' " +
       "GROUP BY EXTRACT(MONTH FROM r.date)")
    List<Object[]> findMonthlyStats();

    @Query("SELECT EXTRACT(YEAR FROM r.date) AS year, COUNT(r.id) AS acceptedCount, COUNT(DISTINCT r.patient.id) AS totalPatients " +
       "FROM RendezVous r " +
       "WHERE r.statutrdv = 'ACCEPTE' " +
       "GROUP BY EXTRACT(YEAR FROM r.date)")
    List<Object[]> findYearlyStats();

    Long countByMedecinIdAndStatutrdv(Long medecinId, StatutRdv statutrdv);


}