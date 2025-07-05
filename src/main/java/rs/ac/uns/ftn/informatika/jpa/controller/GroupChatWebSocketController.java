package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import rs.ac.uns.ftn.informatika.jpa.dto.ChatMessageDTO;
import rs.ac.uns.ftn.informatika.jpa.service.MessageService;

@Controller
public class GroupChatWebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MessageService messageService;

    @MessageMapping("/group.send")  // /app/group.send
    public void sendGroupMessage(@Payload ChatMessageDTO message) {
        // Sačuvaj u bazi
        messageService.saveGroupMessage(message);

        // Pošalji svima u toj grupi
        messagingTemplate.convertAndSend("/topic/group/" + message.getGroupId(), message);
    }
}

