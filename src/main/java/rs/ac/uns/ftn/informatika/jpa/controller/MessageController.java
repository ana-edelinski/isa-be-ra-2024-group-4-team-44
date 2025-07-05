package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.MessageDTO;
import rs.ac.uns.ftn.informatika.jpa.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/chat/{userId1}/{userId2}")
    public ResponseEntity<List<MessageDTO>> getChatHistory(@PathVariable Integer userId1, @PathVariable Integer userId2) {
        List<MessageDTO> chatHistory = messageService.getChatHistory(userId1, userId2);
        return ResponseEntity.ok(chatHistory);
    }

    @GetMapping("/received/{receiverId}")
    public ResponseEntity<List<MessageDTO>> getReceivedMessages(@PathVariable Integer receiverId) {
        List<MessageDTO> receivedMessages = messageService.getReceivedMessages(receiverId);
        return ResponseEntity.ok(receivedMessages);
    }

    @GetMapping("/sent/{senderId}")
    public ResponseEntity<List<MessageDTO>> getSentMessages(@PathVariable Integer senderId) {
        List<MessageDTO> sentMessages = messageService.getSentMessages(senderId);
        return ResponseEntity.ok(sentMessages);
    }

    @PostMapping("/send")
    public ResponseEntity<MessageDTO> sendMessage(@RequestBody MessageDTO messageDTO) {
        MessageDTO sentMessage = messageService.sendMessage(
                messageDTO.getSenderId(),
                messageDTO.getReceiverId(),
                messageDTO.getContent()
        );
        return ResponseEntity.ok(sentMessage);
    }


    @DeleteMapping("/delete/{userId1}/{userId2}")
    public ResponseEntity<Void> deleteChatHistory(@PathVariable Integer userId1, @PathVariable Integer userId2) {
        messageService.deleteChatHistory(userId1, userId2);
        return ResponseEntity.noContent().build();
    }
}
