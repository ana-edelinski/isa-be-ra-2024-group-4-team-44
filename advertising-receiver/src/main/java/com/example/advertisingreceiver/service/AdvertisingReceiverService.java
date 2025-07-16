package com.example.advertisingreceiver.service;


import com.example.advertisingreceiver.dto.AdvertisingMessageDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class AdvertisingReceiverService {

    @RabbitListener(queues = "#{queue.name}")
    public void receiveMessage(AdvertisingMessageDTO message) {
        System.out.println("📢 RECEIVED ADVERTISING MESSAGE 📢");
        System.out.println(message);
        // Ovde možeš dodatno: sačuvaj u bazi, loguj, prikaži u UI, itd.
    }
}
