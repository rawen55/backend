package Telemedcine.cwa.telemedcine.config;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;


public class DirectChannel implements MessageChannel {

    public DirectChannel() {
    }

    @Override
    public boolean send(Message<?> message, long timeout) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}