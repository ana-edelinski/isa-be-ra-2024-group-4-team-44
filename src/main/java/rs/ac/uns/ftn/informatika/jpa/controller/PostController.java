package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.ftn.informatika.jpa.dto.PostDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Post;
import rs.ac.uns.ftn.informatika.jpa.service.PostService;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/{id}")
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
    public ResponseEntity<List<PostDTO>> getPostsByUserId(@PathVariable Integer userId) {
        try {
            List<PostDTO> posts = postService.getUserPostsByUserId(userId);
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/uploadImage")
    public ResponseEntity<PostDTO> uploadImage(@RequestParam("file") MultipartFile file) {
        final String uploadDir = "src/main/resources/static/images/";
        Path uploadDirPath = Paths.get(uploadDir);
        System.out.println("Ime fajla: " + file.getOriginalFilename());
        System.out.println("Veličina fajla: " + file.getSize());

        if (Files.notExists(uploadDirPath)) {
            try {
                Files.createDirectories(uploadDirPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            System.out.println("pozvao se kontroler");

            // Generiši jedinstveno ime za fajl
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path targetLocation = Paths.get(uploadDir, fileName);

            // Sačuvaj fajl na fajl sistemu
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            String imagePath = targetLocation.toString();
            System.out.println("Vraćeni imagePath: " + imagePath);


            PostDTO postDTO = new PostDTO();
            postDTO.setImagePath(imagePath);

            return new ResponseEntity<>(postDTO, HttpStatus.OK);
        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{postId}/likes/count")
    public int getLikesCount(@PathVariable Integer postId) {
        return postService.getLikesCountByPostId(postId);
    }

    @PutMapping("/{postId}/like")
    public ResponseEntity<Void> likeUnlikePost(@PathVariable Integer postId, @RequestParam Integer userId) {
        try {
            postService.likeUnlikePost(postId, userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



}
