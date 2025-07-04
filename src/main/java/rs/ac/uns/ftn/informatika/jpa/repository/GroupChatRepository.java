package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.jpa.model.GroupChat;

import java.util.List;

@Repository
public interface GroupChatRepository extends JpaRepository<GroupChat, Integer> {
    @Query("SELECT DISTINCT g FROM GroupChat g LEFT JOIN FETCH g.members")
    List<GroupChat> findAllWithMembers();

}
