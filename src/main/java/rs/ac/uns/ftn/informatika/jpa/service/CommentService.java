package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.Comment;
import rs.ac.uns.ftn.informatika.jpa.repository.CommentRepository;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) { this.commentRepository = commentRepository; }

    public List<Comment> findByPostId(Integer postId) {
        return commentRepository.findByPostId(postId);
    }

    public List<Comment> findByUserId(Integer userId) {
        return commentRepository.findByUserId(userId);
    }
}
