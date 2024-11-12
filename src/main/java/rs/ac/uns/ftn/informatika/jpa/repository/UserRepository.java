package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.ac.uns.ftn.informatika.jpa.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    //    @Query("select u from User u where u.username = ?1")
    User findByUsername(String username);

    @Query("select u from User u where u.email = ?1")
    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.id = ?1")
    Optional<User> findById(Integer id);

    @Query("select count(u) > 0 from User u where u.username = ?1")
    boolean existsByUsername(String username);

    @Query("select count(u) > 0 from User u where u.email = ?1")
    boolean existsByEmail(String email);

    Optional<User> findByActivationToken(String activationToken);

    @Query("SELECT u FROM User u WHERE " +
            "(:name IS NULL OR u.name = :name) AND " +
            "(:surname IS NULL OR u.surname = :surname) AND " +
            "(:email IS NULL OR u.email = :email) AND " +
            "(:minPosts IS NULL OR SIZE(u.posts) >= :minPosts) AND " +
            "(:maxPosts IS NULL OR SIZE(u.posts) <= :maxPosts)")
    List<User> searchUsers(String name, String surname, String email, Integer minPosts, Integer maxPosts);

    @Query("SELECT u FROM User u ORDER BY SIZE(u.following) ASC")
    List<User> findAllSortedByFollowingCountAsc();

    @Query("SELECT u FROM User u ORDER BY SIZE(u.following) DESC") 
    List<User> findAllSortedByFollowingCountDesc();

    @Query("SELECT u FROM User u ORDER BY u.email ASC")
    List<User> findAllSortedByEmailAsc();

    @Query("SELECT u FROM User u ORDER BY u.email DESC")
    List<User> findAllSortedByEmailDesc();

}