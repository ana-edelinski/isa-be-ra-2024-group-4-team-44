package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.dto.AdvertisingMessageDTO;

@Service
public class AdvertisingProducerService {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public AdvertisingProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendAdvertisingMessage(AdvertisingMessageDTO message) {
        rabbitTemplate.convertAndSend("advertising.exchange", "", message);
    }
}
