package Telemedcine.cwa.telemedcine.service;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
@Service
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificationService(@Qualifier("simpMessagingTemplate") SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendNotification(String destination, Object payload) {
        messagingTemplate.convertAndSend(destination, payload);
    }

    public void notifierPatient(Long id, String message) {
      
        throw new UnsupportedOperationException("Unimplemented method 'notifierPatient'");
    }


}

