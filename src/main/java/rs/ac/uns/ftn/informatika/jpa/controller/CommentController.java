package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.informatika.jpa.dto.CommentDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.PostDTO;
import rs.ac.uns.ftn.informatika.jpa.exception.RateLimitExceededException;
import rs.ac.uns.ftn.informatika.jpa.model.Comment;
import rs.ac.uns.ftn.informatika.jpa.service.CommentService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<CommentDTO> createComment(
            @RequestBody CommentDTO commentDTO) {
        System.out.println(commentDTO.toString());
        try {
            CommentDTO createdComment = commentService.createComment(commentDTO);
            return new ResponseEntity<>(createdComment, HttpStatus.OK);
        } catch (RateLimitExceededException e) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        } catch (Exception e) {
            System.out.println("Greska u kontroleru.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
