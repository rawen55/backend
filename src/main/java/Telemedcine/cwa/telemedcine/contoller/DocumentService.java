package Telemedcine.cwa.telemedcine.contoller;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import Telemedcine.cwa.telemedcine.model.Document;
import Telemedcine.cwa.telemedcine.model.User;
import Telemedcine.cwa.telemedcine.repositories.DocumentRepository;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;//
    }

    public Document saveFile(MultipartFile file, User user) throws IOException {
        Document document = new Document();
        document.setNom(file.getOriginalFilename());
        document.setUrl("/uploads/" + file.getOriginalFilename()); // Exemple de chemin
        document.setData(file.getBytes());
        return documentRepository.save(document);
    }

    public List<Document> getDocumentsByRendezVous(Long rendezvousId) {
        return documentRepository.findByRendezVousId(rendezvousId);
    }
}