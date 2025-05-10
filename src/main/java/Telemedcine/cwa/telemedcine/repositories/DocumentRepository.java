package Telemedcine.cwa.telemedcine.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Telemedcine.cwa.telemedcine.model.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByRendezVousId(Long rendezvousId);
}
