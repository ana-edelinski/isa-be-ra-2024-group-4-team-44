package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.CreateGroupDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.GroupChatDTO;
import rs.ac.uns.ftn.informatika.jpa.service.GroupChatService;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupChatController {

    @Autowired
    private GroupChatService groupChatService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<GroupChatDTO> createGroup(@RequestBody CreateGroupDTO dto) {
        GroupChatDTO createdGroup = groupChatService.createGroup(dto);
        return ResponseEntity.ok(createdGroup);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<GroupChatDTO>> getAllGroups() {
        List<GroupChatDTO> groups = groupChatService.getAllGroups();
        return ResponseEntity.ok(groups);
    }

}
