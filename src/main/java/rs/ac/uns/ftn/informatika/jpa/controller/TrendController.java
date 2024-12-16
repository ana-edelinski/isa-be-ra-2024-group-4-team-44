package rs.ac.uns.ftn.informatika.jpa.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.model.Post;
import rs.ac.uns.ftn.informatika.jpa.service.TrendService;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value ="/api/trends", produces = MediaType.APPLICATION_JSON_VALUE)

public class TrendController {
    @Autowired
    private TrendService trendService;

    // Ukupan broj objava na mreži
    @GetMapping("/total-posts")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<Long> getTotalNumberOfPosts() {
        long totalPosts = trendService.getTotalNumberOfPosts();
        return ResponseEntity.ok(totalPosts);
    }

    // Broj objava u poslednjih 30 dana
    @GetMapping("/posts-last-30-days")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<Long> getNumberOfPostsInLast30Days() {
        long postsLast30Days = trendService.getNumberOfPostsInLast30Days();
        return ResponseEntity.ok(postsLast30Days);
    }


    // 5 najpopularnijih objava u poslednjih 7 dana
    @GetMapping("/top5-posts-last-7-days")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<List<Post>> getTop5PostsInLast7Days() {
        List<Post> top5Posts = trendService.getTop5PostsInLast7Days();
        return ResponseEntity.ok(top5Posts);
    }

    // 10 najpopularnijih objava ikada
    @GetMapping("/top10-posts-all-time")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<List<Post>> getTop10PostsOfAllTime() {
        List<Post> top10Posts = trendService.getTop10PostsOfAllTime();
        return ResponseEntity.ok(top10Posts);
    }

    // 10 korisnika koji su podelili najviše lajkova u poslednjih 7 dana
    @GetMapping("/top10-users-likes-last-7-days")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<List<Map<String, Object>>> getTop10UsersByLikesGivenInLast7Days() {
        // Poziv servisa koji vraća korisnike sa najviše lajkova
        List<Map<String, Object>> topUsers = trendService.getTop10UsersByLikesGivenInLast7Days();
        return ResponseEntity.ok(topUsers); // Vratiti podatke u odgovoru
    }
}
