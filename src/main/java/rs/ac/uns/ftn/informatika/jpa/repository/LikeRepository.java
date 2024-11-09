package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.ac.uns.ftn.informatika.jpa.model.Like;

public interface LikeRepository extends JpaRepository<Like, Integer> {
    @Query("SELECT COUNT(l) FROM Like l WHERE l.post.id = ?1")
    int countByPostId(Integer postId);

    boolean existsByUserIdAndPostId(Integer userId, Integer postId);
}
