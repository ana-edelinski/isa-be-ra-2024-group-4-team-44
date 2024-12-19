package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.dto.CommentDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.PostDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Address;
import rs.ac.uns.ftn.informatika.jpa.model.Like;
import rs.ac.uns.ftn.informatika.jpa.model.Post;
import rs.ac.uns.ftn.informatika.jpa.model.User;
import rs.ac.uns.ftn.informatika.jpa.repository.AddressRepository;
import rs.ac.uns.ftn.informatika.jpa.repository.LikeRepository;
import rs.ac.uns.ftn.informatika.jpa.repository.PostRepository;
import rs.ac.uns.ftn.informatika.jpa.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    public PostDTO getById(Integer id) {
        Post post = postRepository.getPostById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        int likeCount = likeRepository.countByPostId(id);
        String formattedImagePath = post.getImagePath().replace("src\\main\\resources\\static\\", "/");
        return new PostDTO(
                post.getId(),
                post.getCreator().getId(),
                post.getCreator().getUsername(),
                post.getDescription(),
                post.getCreationTime(),
                formattedImagePath,
                post.getLocation() != null ? post.getLocation().getId() : null,
                post.getLocation() != null ? post.getLocation().getStreet() : null,
                post.getLocation() != null ? post.getLocation().getCity() : null,
                post.getLocation() != null ? post.getLocation().getPostalCode() : null,
                post.getComments().stream().map(CommentDTO::new).collect(Collectors.toList()),
                likeCount
        );
    }


    @CacheEvict(value = "postsCache", allEntries = true)
    public PostDTO createPost(PostDTO postDTO) {
        Post post = new Post();
        User user = userRepository.findById(postDTO.getCreatorId())
                .orElseThrow(() -> new NoSuchElementException("User not found"));


        System.out.println("Prolso je exception za usera.");

        post.setCreator(user);
        post.setDescription(postDTO.getDescription());
        post.setCreationTime(postDTO.getCreationTime());
        post.setImagePath(postDTO.getImagePath());

        Address address = new Address();
        address.setCity(postDTO.getLocationCity());
        address.setStreet(postDTO.getLocationStreet());
        address.setPostalCode(postDTO.getLocationPostalCode());
        addressRepository.save(address);
        post.setLocation(address);

        postRepository.save(post);

        return postDTO;
    }

    @CachePut(value = "postsCache", key = "#postId")
    public PostDTO updatePost(Integer id, PostDTO postDTO, Integer userId) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getCreator().getId().equals(userId)) {
            throw new RuntimeException("User not authorized to update this post");
        }

        post.setDescription(postDTO.getDescription());
        post.setImagePath(postDTO.getImagePath());
        postRepository.save(post);

        return getById(id);
    }

    @CacheEvict(value = "postsCache", key = "#postId")
    @Transactional
    public void deletePost(Integer id, Integer userId) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getCreator().getId().equals(userId)) {
            throw new RuntimeException("User not authorized to delete this post");
        }

        postRepository.deleteCommentsByPostId(id);
        postRepository.deleteLikesByPostId(id);
        postRepository.deletePostById(id);
    }

    public List<PostDTO> getUserPostsByUserId(Integer userId) {
        List<Post> userPosts = postRepository.findByUserIdWithComments(userId);

        return userPosts.stream()
                .map(PostDTO::new)
                .collect(Collectors.toList());
    }

    public List<PostDTO> getAll() {
        List<Post> userPosts = postRepository.findAll();

        return userPosts.stream()
                .map(PostDTO::new)
                .collect(Collectors.toList());
    }

    public void likeUnlikePost(Integer postId, Integer userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // TODO: Da li da imamo ovo pravilo?
        //if (post.getCreator().getId().equals(userId)) {
            //throw new RuntimeException("User cannot like their own post");
        //}

        Optional<Like> existingLike = likeRepository.findByPostAndUser(post, user);
        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
        } else {
            Like newLike = new Like();
            newLike.setPost(post);
            newLike.setUser(user);
            newLike.setCreationTime(LocalDateTime.now());
            likeRepository.save(newLike);
        }
    }

    public List<PostDTO> getPostsFromFollowing(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Set<User> followingUsers = user.getFollowing();

        if (followingUsers.isEmpty()) {
            return new ArrayList<>();
        }

        List<Post> posts = postRepository.findPostsByFollowingUsers(followingUsers);
        return posts.stream()
                .map(PostDTO::new)
                .collect(Collectors.toList());
    }

}
