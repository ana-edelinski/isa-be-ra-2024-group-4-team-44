package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.ac.uns.ftn.informatika.jpa.model.Like;
import rs.ac.uns.ftn.informatika.jpa.model.Post;
import rs.ac.uns.ftn.informatika.jpa.model.User;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Integer> {
    @Query("SELECT COUNT(l) FROM Like l WHERE l.post.id = ?1")
    int countByPostId(Integer postId);

    Optional<Like> findByPostAndUser(Post post, User user);
}
