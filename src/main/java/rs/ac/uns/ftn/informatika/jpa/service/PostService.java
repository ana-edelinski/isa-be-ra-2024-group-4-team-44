package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.dto.CommentDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.PostDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Post;
import rs.ac.uns.ftn.informatika.jpa.repository.LikeRepository;
import rs.ac.uns.ftn.informatika.jpa.repository.PostRepository;

import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private LikeRepository likeRepository;

    public PostDTO getById(Integer id) {
        Post post = postRepository.getById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        int likeCount = likeRepository.countByPostId(id);
        return new PostDTO(
                post.getId(),
                post.getCreator().getId(),
                post.getCreator().getUsername(),
                post.getDescription(),
                post.getCreationTime(),
                post.getImagePath(),
                post.getLocation() != null ? post.getLocation().getId() : null,
                post.getLocation() != null ? post.getLocation().getStreet() : null,
                post.getLocation() != null ? post.getLocation().getCity() : null,
                post.getLocation() != null ? post.getLocation().getPostalCode() : null,
                post.getComments().stream().map(CommentDTO::new).collect(Collectors.toList()),
                likeCount
        );

    }

}
