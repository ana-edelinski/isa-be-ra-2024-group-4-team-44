package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.CreateGroupDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.GroupChatDTO;
import rs.ac.uns.ftn.informatika.jpa.service.GroupChatService;
import org.springframework.security.core.Authentication;
import rs.ac.uns.ftn.informatika.jpa.model.User;
import rs.ac.uns.ftn.informatika.jpa.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupChatController {

    @Autowired
    private GroupChatService groupChatService;

    @Autowired
    private UserRepository userRepository;

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

    @PutMapping("/{groupId}/members")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Void> updateGroupMembers(@PathVariable Integer groupId, @RequestBody List<Integer> memberIds) {
        groupChatService.updateGroupMembers(groupId, memberIds);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/my")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<GroupChatDTO>> getMyGroups(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findOptionalByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<GroupChatDTO> groups = groupChatService.getGroupsForUser(user.getId());
        return ResponseEntity.ok(groups);
    }

}
