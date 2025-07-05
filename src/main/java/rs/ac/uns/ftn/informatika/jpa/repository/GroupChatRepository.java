package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.jpa.model.GroupChat;

import java.util.List;

@Repository
public interface GroupChatRepository extends JpaRepository<GroupChat, Integer> {
    @Query(value = "SELECT * FROM group_chat WHERE member_ids @> CAST(CONCAT('[', :userId, ']') AS jsonb)", nativeQuery = true)
    List<GroupChat> findByMemberIdsContaining(@Param("userId") Integer userId);
}
