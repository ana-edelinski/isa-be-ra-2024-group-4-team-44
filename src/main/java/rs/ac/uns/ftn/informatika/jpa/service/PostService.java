package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.dto.PostDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Post;
import rs.ac.uns.ftn.informatika.jpa.repository.PostRepository;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public PostDTO getById(Integer id) {
        Post post = postRepository.getById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        return new PostDTO(post);
    }

}
