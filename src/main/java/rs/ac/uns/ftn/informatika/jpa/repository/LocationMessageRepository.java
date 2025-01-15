package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.ac.uns.ftn.informatika.jpa.model.LocationMessage;
import rs.ac.uns.ftn.informatika.jpa.model.User;

import java.util.Optional;

public interface LocationMessageRepository extends JpaRepository<LocationMessage, Integer> {
    @Query("select l from LocationMessage l where l.id = ?1")
    Optional<LocationMessage> findById(Integer id);

}
