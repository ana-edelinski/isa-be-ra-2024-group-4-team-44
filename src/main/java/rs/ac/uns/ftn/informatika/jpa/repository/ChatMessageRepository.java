package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.informatika.jpa.model.ChatMessage;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findTop10ByGroupIdOrderByTimestampDesc(Integer groupId);
    List<ChatMessage> findByGroupIdOrderByTimestampAsc(Integer groupId);
}

