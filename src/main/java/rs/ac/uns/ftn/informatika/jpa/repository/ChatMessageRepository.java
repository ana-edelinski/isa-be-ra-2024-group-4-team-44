package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.informatika.jpa.model.ChatMessage;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByGroupIdOrderByTimestampAsc(Integer groupId);

    List<ChatMessage> findTop10ByGroupIdAndTimestampBeforeOrderByTimestampDesc(
            Integer groupId, LocalDateTime before
    );

    List<ChatMessage> findByGroupIdAndTimestampGreaterThanEqualOrderByTimestampAsc(
            Integer groupId, LocalDateTime joinedAt
    );
}


