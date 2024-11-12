package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import rs.ac.uns.ftn.informatika.jpa.model.Post;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface PostRepository  extends JpaRepository<Post, Integer> {
    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.comments WHERE p.id = ?1")
    Optional<Post> getPostById(Integer id);

    @Query("SELECT DISTINCT p FROM Post p LEFT JOIN FETCH p.comments WHERE p.creator.id = ?1")
    List<Post> findByUserIdWithComments(Integer userId);

    @Query("SELECT DISTINCT p FROM Post p LEFT JOIN FETCH p.comments")
    List<Post> findAll();

    @Modifying
    @Transactional
    @Query("DELETE FROM Comment c WHERE c.post.id = :postId")
    void deleteCommentsByPostId(Integer postId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Like l WHERE l.post.id = :postId")
    void deleteLikesByPostId(Integer postId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Post p WHERE p.id = :postId")
    void deletePostById(Integer postId);
}
