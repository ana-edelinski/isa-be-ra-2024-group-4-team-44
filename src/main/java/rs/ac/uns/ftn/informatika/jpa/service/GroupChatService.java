package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.dto.CreateGroupDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.GroupChatDTO;
import rs.ac.uns.ftn.informatika.jpa.model.GroupChat;
import rs.ac.uns.ftn.informatika.jpa.model.User;
import rs.ac.uns.ftn.informatika.jpa.repository.GroupChatRepository;
import rs.ac.uns.ftn.informatika.jpa.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupChatService {
    @Autowired
    private GroupChatRepository groupChatRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public GroupChatDTO createGroup(CreateGroupDTO dto) {
        GroupChat group = new GroupChat();
        group.setName(dto.getName());

        List<User> users = userRepository.findAllById(dto.getMemberIds());
        group.setMembers(new HashSet<>(users));

        groupChatRepository.save(group);
        return new GroupChatDTO(group);
    }

    @Transactional
    public List<GroupChatDTO> getAllGroups() {
        List<GroupChat> groups = groupChatRepository.findAllWithMembers();
        return groups.stream()
                .map(GroupChatDTO::new)
                .collect(Collectors.toList());
    }

}
