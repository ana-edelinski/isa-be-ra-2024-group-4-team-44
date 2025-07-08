package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.jpa.model.GroupMembership;

import java.util.Optional;

@Repository
public interface GroupMembershipRepository extends JpaRepository<GroupMembership, Long> {

    Optional<GroupMembership> findByGroupIdAndUserId(Integer groupId, Integer userId);

    boolean existsByGroupIdAndUserId(Integer groupId, Integer userId);
}
