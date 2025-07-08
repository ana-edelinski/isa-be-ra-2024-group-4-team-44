package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.dto.ChatMessageDTO;
import rs.ac.uns.ftn.informatika.jpa.model.ChatMessage;
import rs.ac.uns.ftn.informatika.jpa.model.GroupMembership;
import rs.ac.uns.ftn.informatika.jpa.repository.ChatMessageRepository;
import rs.ac.uns.ftn.informatika.jpa.repository.GroupMembershipRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupMembershipService {

    @Autowired
    private GroupMembershipRepository membershipRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Transactional
    public void addMembers(Integer groupId, List<Integer> userIds) {
        LocalDateTime now = LocalDateTime.now();

        for (Integer userId : userIds) {
            if (!membershipRepository.existsByGroupIdAndUserId(groupId, userId)) {
                GroupMembership membership = new GroupMembership();
                membership.setGroupId(groupId);
                membership.setUserId(userId);
                membership.setJoinedAt(now);
                membership.setSeededHistory(false);
                membershipRepository.save(membership);
            }
        }
    }

    @Transactional
    public List<ChatMessageDTO> getHistoryForUser(Integer groupId, Integer userId) {
        GroupMembership membership = membershipRepository.findByGroupIdAndUserId(groupId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Not a member of this group."));

        LocalDateTime joinedAt = membership.getJoinedAt();

        // 1. poslednjih 10 pre joinedAt
        List<ChatMessage> before = chatMessageRepository
                .findTop10ByGroupIdAndTimestampBeforeOrderByTimestampDesc(groupId, joinedAt);

        // 2. sve od joinedAt nadalje
        List<ChatMessage> after = chatMessageRepository
                .findByGroupIdAndTimestampGreaterThanEqualOrderByTimestampAsc(groupId, joinedAt);

        List<ChatMessage> combined = new ArrayList<>();
        Collections.reverse(before); // da pre bude oldest->newest
        combined.addAll(before);
        combined.addAll(after);

        return combined.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private ChatMessageDTO toDTO(ChatMessage message) {
        ChatMessageDTO dto = new ChatMessageDTO();
        dto.setContent(message.getContent());
        dto.setSenderId(message.getSenderId());
        dto.setSenderName(message.getSenderName());
        dto.setGroupId(message.getGroupId());
        dto.setTimestamp(message.getTimestamp());
        return dto;
    }
}
