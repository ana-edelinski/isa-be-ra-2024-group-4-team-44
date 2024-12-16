package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.Post;
import rs.ac.uns.ftn.informatika.jpa.model.User;
import rs.ac.uns.ftn.informatika.jpa.repository.LikeRepository;
import rs.ac.uns.ftn.informatika.jpa.repository.PostRepository;
import rs.ac.uns.ftn.informatika.jpa.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class TrendService {
    private static final Logger log = LoggerFactory.getLogger(TrendService.class);
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PostRepository postRepository;

    @Autowired
    private final LikeRepository likeRepository;

    @Autowired
    public TrendService(UserRepository userRepository, PostRepository postRepository, LikeRepository likeRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
    }
    public long getTotalNumberOfPosts() {
        return postRepository.countAllPosts(); // Pretpostavlja se da je metoda definisana u PostRepository.
    }

    public long getNumberOfPostsInLast30Days() {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        return postRepository.countByCreationTimeAfter(thirtyDaysAgo);
    }


    public List<Post> getTop5PostsInLast7Days() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        log.info("Fetching top 5 posts created after: {}", sevenDaysAgo);
        List<Post> posts = postRepository.findTop5ByCreationTimeAfterOrderByLikesDesc(sevenDaysAgo);
        log.info("Fetched posts: {}", posts.size());
        return posts;
    }


    public List<Post> getTop10PostsOfAllTime() {
        List<Post> topPosts = postRepository.findTop10PostsOfAllTime();
        return topPosts.size() > 10 ? topPosts.subList(0, 10) : topPosts;  // Obezbeđuje da ne pređemo 10 postova
    }




    public List<Map<String, Object>> getTop10UsersByLikesGivenInLast7Days() {
        // Datum pre 7 dana
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        log.info("Datum pre 7 dana: {}", sevenDaysAgo); // Loguj datum pre 7 dana

        // Pozivanje repository metode koja vraća korisnike sa najviše lajkova u poslednjih 7 dana
        List<Object[]> results = likeRepository.findTop10UsersByLikesGivenInLast7Days(sevenDaysAgo);
        log.info("Broj rezultata: {}", results.size()); // Loguj broj rezultata koji su vraćeni

        // Lista koja će sadržati podatke o korisnicima
        List<Map<String, Object>> topUsers = new ArrayList<>();

        // Obrada rezultata i priprema za odgovor
        for (Object[] result : results) {
            Integer userId = (Integer) result[0]; // Prvi element je ID korisnika (Integer)
            Long likeCount = (Long) result[1]; // Drugi element je broj lajkova

            // Preuzimanje korisnika sa ID-jem
            User user = userRepository.findById(userId).orElse(null); // Pretpostavljam da imaš repository za korisnike

            if (user != null) {
                log.debug("Korisnik: {}, Lajkovi: {}", user.getUsername(), likeCount); // Loguj svakog korisnika i broj lajkova

                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("username", user.getUsername()); // Dodaj username
                userInfo.put("likeCount", likeCount); // Dodaj broj lajkova

                topUsers.add(userInfo); // Dodaj korisnika u listu
            }
        }

        log.info("Vraćeni top korisnici: {}", topUsers.size()); // Loguj broj vraćenih korisnika
        return topUsers; // Vratiti listu sa top korisnicima
    }



}
