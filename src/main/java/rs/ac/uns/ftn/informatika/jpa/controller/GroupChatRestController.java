package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.ChatMessageDTO;
import rs.ac.uns.ftn.informatika.jpa.service.GroupMembershipService;
import rs.ac.uns.ftn.informatika.jpa.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/api/groupchat")
public class GroupChatRestController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private GroupMembershipService membershipService;

    @PostMapping("/{groupId}/add-members")
    public ResponseEntity<Void> addMembers(
            @PathVariable Integer groupId,
            @RequestBody List<Integer> userIds
    ) {
        membershipService.addMembers(groupId, userIds);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{groupId}/history/{userId}")
    public ResponseEntity<List<ChatMessageDTO>> getChatHistory(
            @PathVariable Integer groupId,
            @PathVariable Integer userId
    ) {
        return ResponseEntity.ok(membershipService.getHistoryForUser(groupId, userId));
    }


}
