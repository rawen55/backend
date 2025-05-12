package Telemedcine.cwa.telemedcine.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
@Service
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendNotificationToPatient(String patientUsername, String message) {
        messagingTemplate.convertAndSendToUser(
            patientUsername, "/topic/notifications", message
        );
    }
    
}

