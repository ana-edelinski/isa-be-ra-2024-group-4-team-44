package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.informatika.jpa.model.Like;
import rs.ac.uns.ftn.informatika.jpa.model.Post;
import rs.ac.uns.ftn.informatika.jpa.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Integer> {
    @Query("SELECT COUNT(l) FROM Like l WHERE l.post.id = ?1")
    int countByPostId(Integer postId);

    Optional<Like> findByPostAndUser(Post post, User user);

    @Query("SELECT l.user.id, COUNT(l) AS likeCount " +
            "FROM Like l " +
            "WHERE l.creationTime > :date " + // filtriraj po vremenu
            "GROUP BY l.user.id " + // grupiši po korisnicima
            "ORDER BY likeCount DESC") // sortiraj po broju lajkova
    List<Object[]> findTop10UsersByLikesGivenInLast7Days(@Param("date") LocalDateTime date);

}
