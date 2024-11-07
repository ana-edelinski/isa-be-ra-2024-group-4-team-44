package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.informatika.jpa.dto.CommentDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.PostDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Comment;
import rs.ac.uns.ftn.informatika.jpa.model.Post;
import rs.ac.uns.ftn.informatika.jpa.service.PostService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();

        List<PostDTO> postsDTO = new ArrayList<>();
        for (Post p : posts) {
            postsDTO.add(new PostDTO(p));
        }

        return new ResponseEntity<>(postsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Integer id) {
        PostDTO postDTO = postService.getPostWithComments(id);

        if (postDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

//    @GetMapping(value = "/{postId}/comments")
//    public ResponseEntity<List<CommentDTO>> getCommentsForPost(@PathVariable Integer postId) {
//        Post post = postService.getPostById(postId);
//
//        if (post == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        List<Comment> comments = post.getComments();
//        List<CommentDTO> commentsDTO = new ArrayList<>();
//
//        for (Comment c : comments) {
//            commentsDTO.add(new CommentDTO(c));
//        }
//
//        return new ResponseEntity<>(commentsDTO, HttpStatus.OK);
//    }

}
