package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.dto.ChatMessageDTO;
import rs.ac.uns.ftn.informatika.jpa.model.ChatMessage;
import rs.ac.uns.ftn.informatika.jpa.repository.ChatMessageRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public void saveGroupMessage(ChatMessageDTO dto) {
        ChatMessage msg = new ChatMessage();
        msg.setContent(dto.getContent());
        msg.setSenderId(dto.getSenderId());
        msg.setSenderName(dto.getSenderName());
        msg.setGroupId(dto.getGroupId());
        msg.setTimestamp(LocalDateTime.now());
        chatMessageRepository.save(msg);
    }
}
