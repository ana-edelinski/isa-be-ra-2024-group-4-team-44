package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.informatika.jpa.model.Post;

import java.time.LocalDateTime;
import java.util.Set;

public interface ActivityReportRepository extends JpaRepository<Post, Integer> {

    @Query("SELECT COUNT(c) FROM Comment c " +
            "WHERE c.post.id IN (" +
            "  SELECT p.id FROM Post p WHERE p.creator.id = :userId" +
            ") " +
            "AND c.creationTime > :lastActivity")
    Long countNewComments(@Param("userId") Integer userId, @Param("lastActivity") LocalDateTime lastActivity);


    @Query("SELECT COUNT(l) FROM Like l " +
            "WHERE l.post.id IN (" +
            "  SELECT p.id FROM Post p WHERE p.creator.id = :userId" +
            ") " +
            "AND l.creationTime > :lastActivity")
    Long countNewLikes(@Param("userId") Integer userId, @Param("lastActivity") LocalDateTime lastActivity);

    @Query("SELECT COUNT(p) FROM Post p " +
            "WHERE p.creator.id IN :followingIds " +
            "AND p.creationTime > :lastActivity")
    Long countNewPostsFromFollowed(
            @Param("followingIds") Set<Integer> followingIds,
            @Param("lastActivity") LocalDateTime lastActivity
    );

}

