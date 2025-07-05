package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.dto.CreateGroupDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.GroupChatDTO;
import rs.ac.uns.ftn.informatika.jpa.model.GroupChat;
import rs.ac.uns.ftn.informatika.jpa.model.GroupMembership;
import rs.ac.uns.ftn.informatika.jpa.model.User;
import rs.ac.uns.ftn.informatika.jpa.repository.GroupChatRepository;
import rs.ac.uns.ftn.informatika.jpa.repository.GroupMembershipRepository;
import rs.ac.uns.ftn.informatika.jpa.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GroupChatService {
    @Autowired
    private GroupChatRepository groupChatRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupMembershipRepository membershipRepository;

    @Transactional
    public GroupChatDTO createGroup(CreateGroupDTO dto) {
        LocalDateTime now = LocalDateTime.now();

        GroupChat group = new GroupChat();
        group.setName(dto.getName());
        group.setMemberIds(dto.getMemberIds());
        group.setCreatorId(dto.getCreatorId());
        groupChatRepository.save(group);

        // upisi membership za sve
        for (Integer userId : dto.getMemberIds()) {
            GroupMembership membership = new GroupMembership();
            membership.setGroupId(group.getId());
            membership.setUserId(userId);
            membership.setJoinedAt(now);
            membershipRepository.save(membership);
        }

        return new GroupChatDTO(group);
    }



    @Transactional
    public List<GroupChatDTO> getAllGroups() {
        List<GroupChat> groups = groupChatRepository.findAll();
        return groups.stream()
                .map(GroupChatDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateGroupMembers(Integer groupId, List<Integer> memberIds) {
        GroupChat group = groupChatRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));
        group.setMemberIds(memberIds);
        groupChatRepository.save(group);

        LocalDateTime now = LocalDateTime.now();
        for (Integer userId : memberIds) {
            if (!membershipRepository.existsByGroupIdAndUserId(groupId, userId)) {
                GroupMembership membership = new GroupMembership();
                membership.setGroupId(groupId);
                membership.setUserId(userId);
                membership.setJoinedAt(now);
                membershipRepository.save(membership);
            }
        }
    }


    @Transactional
    public List<GroupChatDTO> getGroupsForUser(Integer userId) {
        List<GroupChat> groups = groupChatRepository.findByMemberIdsContaining(userId);
        return groups.stream()
                .map(GroupChatDTO::new)
                .collect(Collectors.toList());
    }




}
