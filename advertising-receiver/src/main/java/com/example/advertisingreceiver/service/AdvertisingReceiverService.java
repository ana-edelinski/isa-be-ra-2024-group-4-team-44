package com.example.advertisingreceiver.service;


import com.example.advertisingreceiver.dto.AdvertisingMessageDTO;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class AdvertisingReceiverService {

    //value je prazan string jer dobija random ime
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "", durable = "false", exclusive = "false", autoDelete = "true"),
            exchange = @Exchange(value = "advertising.exchange", type = "fanout")
    ))
    public void receiveMessage(AdvertisingMessageDTO message) {
        System.out.println("📢 RECEIVED ADVERTISING MESSAGE 📢");
        System.out.println(message);
    }
}
