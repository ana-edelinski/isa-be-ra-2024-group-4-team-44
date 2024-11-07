package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.ac.uns.ftn.informatika.jpa.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository  extends JpaRepository<Post, Integer> {

    @Query("select p from Post p")
    List<Post> getAll();

    @Query("select p from Post p where p.id = ?1")
    Post getById(Integer id);

}
