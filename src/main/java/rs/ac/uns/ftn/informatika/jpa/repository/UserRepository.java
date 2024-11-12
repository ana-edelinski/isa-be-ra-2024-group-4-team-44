package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
            "(:name = '' OR LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:surname = '' OR LOWER(u.surname) LIKE LOWER(CONCAT('%', :surname, '%'))) AND " +
            "(:email = '' OR LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND " +
            "(:minPosts = 0 OR SIZE(u.posts) >= :minPosts) AND " +
            "(:maxPosts = 2147483647 OR SIZE(u.posts) <= :maxPosts)")
    List<User> searchUsers(@Param("name") String name,
                           @Param("surname") String surname,
                           @Param("email") String email,
                           @Param("minPosts") Integer minPosts,
                           @Param("maxPosts") Integer maxPosts);



    @Query("SELECT u FROM User u ORDER BY SIZE(u.following) ASC")
    List<User> findAllSortedByFollowingCountAsc();

    @Query("SELECT u FROM User u ORDER BY SIZE(u.following) DESC") 
    List<User> findAllSortedByFollowingCountDesc();

    @Query("SELECT u FROM User u ORDER BY u.email ASC")
    List<User> findAllSortedByEmailAsc();

    @Query("SELECT u FROM User u ORDER BY u.email DESC")
    List<User> findAllSortedByEmailDesc();

    @Query(value = "SELECT ur.role_id FROM user_role ur WHERE ur.user_id = :userId", nativeQuery = true)
    Integer findRoleIdByUserId(@Param("userId") Integer userId);
}

