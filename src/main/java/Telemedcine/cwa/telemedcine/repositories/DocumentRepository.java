package Telemedcine.cwa.telemedcine.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import Telemedcine.cwa.telemedcine.model.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}

