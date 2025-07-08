package rs.ac.uns.ftn.informatika.jpa.service;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.informatika.jpa.dto.CommentDTO;
import rs.ac.uns.ftn.informatika.jpa.exception.RateLimitExceededException;
import rs.ac.uns.ftn.informatika.jpa.model.Comment;
import rs.ac.uns.ftn.informatika.jpa.model.Post;
import rs.ac.uns.ftn.informatika.jpa.model.User;
import rs.ac.uns.ftn.informatika.jpa.repository.CommentRepository;
import rs.ac.uns.ftn.informatika.jpa.repository.PostRepository;
import rs.ac.uns.ftn.informatika.jpa.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class CommentService {

    private final Logger LOG = LoggerFactory.getLogger(UserService.class);
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }
    @Transactional
    @RateLimiter(name = "comment-limit", fallbackMethod = "commentLimitFallback")
    public CommentDTO createComment(CommentDTO commentDTO){

        Post post = postRepository.findById(commentDTO.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        User user = userRepository.findById(commentDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Comment comment = new Comment();
        comment.setText(commentDTO.getText());
        comment.setPost(post);
        comment.setUser(user);
        comment.setCreationTime(commentDTO.getCreationTime());
        commentRepository.save(comment);


        return new CommentDTO(comment);
    }


    public CommentDTO commentLimitFallback(CommentDTO commentDTO, RequestNotPermitted ex) {
        LOG.warn("Rate limit exceeded for createComment call");

        throw new RateLimitExceededException("Rate limit exceeded. Please try again later.");
    }
}
