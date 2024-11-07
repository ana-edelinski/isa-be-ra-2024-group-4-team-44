package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.ac.uns.ftn.informatika.jpa.model.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
//    Page<Post> findAll(Pageable pageable);
    @Query("select p from Post p join fetch p.comments where p.id = ?1")
    Post findOneWithComments(Integer postId);
}
