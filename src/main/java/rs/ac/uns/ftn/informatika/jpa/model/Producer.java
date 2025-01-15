package rs.ac.uns.ftn.informatika.jpa.model;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.informatika.jpa.dto.LocationMessageDTO;

@Component
public class Producer {
    private static final Logger log = LoggerFactory.getLogger(Producer.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    ObjectMapper objectMapper;

    public void sendTo(String routingkey, LocationMessageDTO message){
        try {
            // ObjectMapper objectMapper = new ObjectMapper();
            //  String messageJson = objectMapper.writeValueAsString(message);
            String messageJson = objectMapper.writeValueAsString(message);
            log.info("Sending> ... Message=[ " + messageJson + " ] RoutingKey=[" + routingkey + "]");
            this.rabbitTemplate.convertAndSend(routingkey, messageJson);
        } catch (Exception e) {
            log.error("Error while sending message: ", e);
        }
    }

}
