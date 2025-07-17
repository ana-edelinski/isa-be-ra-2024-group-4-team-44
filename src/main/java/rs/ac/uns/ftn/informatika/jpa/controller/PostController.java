package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.ftn.informatika.jpa.dto.PostDTO;
import rs.ac.uns.ftn.informatika.jpa.service.PostService;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Integer id) {
        try {
            PostDTO postDTO = postService.getById(id);
            return new ResponseEntity<>(postDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        try {
            System.out.println("Usao u kontroler.");
            List<PostDTO> posts = postService.getAll();
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PostMapping()
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<PostDTO> cratePost(
            @RequestBody PostDTO postDTO) {
        try {
            PostDTO cratedPost = postService.createPost(postDTO);
            return new ResponseEntity<>(cratedPost, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Greska u kontroleru.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<PostDTO> updatePost(
            @PathVariable Integer id,
            @RequestBody PostDTO postDTO,
            @RequestParam Integer userId) {
        try {
            PostDTO updatedPost = postService.updatePost(id, postDTO, userId);
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<Void> deletePost(@PathVariable Integer id, @RequestParam Integer userId) {
        try {
            postService.deletePost(id, userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Post not found")) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            else if (e.getMessage().equals("User not authorized to delete this post")) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{userId}")
    //@PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<List<PostDTO>> getPostsByUserId(@PathVariable Integer userId) {
        try {
            List<PostDTO> posts = postService.getUserPostsByUserId(userId);
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Value("${onlybuns.upload.path}")
    private String uploadDir;

    @PostMapping("/uploadImage")
    //@PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<PostDTO> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            Path uploadPath = Paths.get(uploadDir);

            if (Files.notExists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path targetLocation = uploadPath.resolve(fileName);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // Vrati relativnu putanju koju frontend koristi
            String imagePath = "/images/" + fileName;

            PostDTO postDTO = new PostDTO();
            postDTO.setImagePath(imagePath);

            return new ResponseEntity<>(postDTO, HttpStatus.OK);
        } catch (IOException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{postId}/like")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<Void> likeUnlikePost(@PathVariable Integer postId, @RequestParam Integer userId) {
        try {
            postService.likeUnlikePost(postId, userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/following/{userId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<List<PostDTO>> getPostsFromFollowing(@PathVariable Integer userId) {
        try {
            List<PostDTO> posts = postService.getPostsFromFollowing(userId);
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/nearby")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<List<PostDTO>> getNearbyPosts(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam Double radius) {
        List<PostDTO> nearbyPosts = postService.findNearbyPosts(latitude, longitude, radius);
        return ResponseEntity.ok(nearbyPosts);
    }

    @PutMapping("/advertise/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> markPostAsAdvertised(@PathVariable Integer id) {
        postService.markAsAdvertised(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @PutMapping("/simulate-conflict/{id}")
    public ResponseEntity<Void> simulateConflictResolution(@PathVariable Integer id) {
        postService.simulateConflictResolution(id);
        return ResponseEntity.ok().build();
    }


}
