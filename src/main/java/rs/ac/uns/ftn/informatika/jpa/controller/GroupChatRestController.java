package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.ChatMessageDTO;
import rs.ac.uns.ftn.informatika.jpa.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/api/groupchat")
public class GroupChatRestController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/{groupId}/last10")
    public ResponseEntity<List<ChatMessageDTO>> getLast10GroupMessages(@PathVariable Integer groupId) {
        return ResponseEntity.ok(messageService.getLast10GroupMessages(groupId));
    }

    @GetMapping("/{groupId}/all")
    public ResponseEntity<List<ChatMessageDTO>> getAllGroupMessages(@PathVariable Integer groupId) {
        return ResponseEntity.ok(messageService.getAllGroupMessages(groupId));
    }

}
