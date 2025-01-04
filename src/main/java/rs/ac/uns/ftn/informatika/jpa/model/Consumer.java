package rs.ac.uns.ftn.informatika.jpa.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import rs.ac.uns.ftn.informatika.jpa.dto.LocationMessageDTO;

@Component
public class Consumer {


    @Autowired
    ObjectMapper objectMapper;
    private static final Logger log = LoggerFactory.getLogger(Consumer.class);

    @RabbitListener(queues = "${myqueue2}")
    public void handler(LocationMessageDTO locationMessage) {
        try {
            log.info("Received LocationMessage: " + locationMessage.getName());
            // You can now process the LocationMessageDTO (e.g., save it to the database)
        } catch (Exception e) {
            log.error("Error while processing message: ", e);
        }
    }
   // private static final Logger log = LoggerFactory.getLogger(Consumer.class);

//    @RabbitListener(queues="${myqueue2}")
//    public void handler(byte[] message) {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            LocationMessageDTO locationMessage = objectMapper.readValue(message, LocationMessageDTO.class);
//
//            log.info("Received LocationMessageDTO: " + locationMessage.getName());
//            // Ovde možeš dodati kod za čuvanje u bazu
//        } catch (Exception e) {
//            log.error("Error while processing message: ", e);
//        }
//    }
}