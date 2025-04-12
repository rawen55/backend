package Telemedcine.cwa.telemedcine.service;
import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import Telemedcine.cwa.telemedcine.model.Document;
import Telemedcine.cwa.telemedcine.model.User;
import Telemedcine.cwa.telemedcine.repositories.DocumentRepository;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public Document saveFile(MultipartFile file, User user) throws IOException {
        Document document = new Document();
        document.setName(file.getOriginalFilename());
        document.setType(file.getContentType());
        document.setData(file.getBytes()); 
        document.setUser(user);

        return documentRepository.save(document);
    }

    public Optional<Document> getFile(Long id) {
        return documentRepository.findById(id);
    }
}
