package Telemedcine.cwa.telemedcine.contoller;
import java.io.IOException;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import Telemedcine.cwa.telemedcine.model.Document;
import Telemedcine.cwa.telemedcine.model.User;
import Telemedcine.cwa.telemedcine.service.DocumentService;

@RestController
@RequestMapping("api/documents")
public class DocumentController {
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, 
                                        @AuthenticationPrincipal User user) {
        try {
            Document savedFile = documentService.saveFile(file, user);
            return ResponseEntity.ok("Fichier " + savedFile.getName() + " uploadé avec succès !");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'upload");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable Long id) {
        Optional<Document> fileOptional = documentService.getFile(id);
        if (fileOptional.isPresent()) {
            Document file = fileOptional.get();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .body(file.getData());
        }
        return ResponseEntity.notFound().build();
    }
}
