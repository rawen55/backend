package Telemedcine.cwa.telemedcine.contoller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chatbot")

@CrossOrigin("*")
public class ChatBotController {

    @PostMapping("/ask")
    public ResponseEntity<Map<String, String>> askBot(@RequestBody Map<String, String> payload) {
        String question = payload.get("message").toLowerCase();
        String response = "Désolé, je n'ai pas compris votre question.";

        // Réponses pour les questions sur les rendez-vous
        if (question.contains("prendre un rendez-vous") || question.contains("rendez-vous")) {
            response = "Pour prendre un rendez-vous, vous pouvez choisir un médecin disponible, choisir la date et l'heure qui vous conviennent, et soumettre votre demande. Voulez-vous voir les médecins disponibles aujourd'hui ?";
        } else if (question.contains("annuler un rendez-vous") || question.contains("annuler")) {
            response = "Oui, vous pouvez annuler un rendez-vous à tout moment. Si vous avez déjà un rendez-vous, je peux vous aider à l'annuler. Voulez-vous annuler un rendez-vous ?";
        } else if (question.contains("médecins disponibles") || question.contains("médecin")) {
            // Remplace cette liste par les données réelles récupérées depuis ton backend
            response = "Voici la liste des médecins disponibles aujourd'hui :\n1. Dr. Sami Gharbi (Cardiologue)\n2. Dr. Nadia Ben Ali (Pédiatre)\n3. Dr. Rami Jaziri (Dermatologue)\nSi vous voulez prendre rendez-vous avec l'un d'eux, je peux vous aider à fixer un rendez-vous.";
        } else if (question.contains("anciens rendez-vous") || question.contains("consulter rendez-vous")) {
            response = "Vous pouvez consulter vos anciens rendez-vous directement dans votre tableau de bord sur l’application Dawini. Voulez-vous que je vous guide pour y accéder ?";
        } else if (question.contains("prix consultation") || question.contains("coût téléconsultation")) {
            response = "Le coût d'une téléconsultation est de 70 TND. Vous pouvez effectuer le paiement en ligne lors de la prise de rendez-vous. Souhaitez-vous prendre un rendez-vous ?";
        }
        
        Map<String, String> res = new HashMap<>();
        res.put("response", response);
        return ResponseEntity.ok(res);
    }
}