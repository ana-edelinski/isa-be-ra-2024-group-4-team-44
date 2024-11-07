package rs.ac.uns.ftn.informatika.jpa.dto;

import rs.ac.uns.ftn.informatika.jpa.model.Post;

import java.util.List;
import java.util.stream.Collectors;

public class PostDTO {
    private Integer id;
    private String description;
    private String imageUrl;
    private int likesCount;
    private List<CommentDTO> comments;

    public PostDTO(Post post) {
        this.id = post.getId();
        this.description = post.getDescription();
        this.imageUrl = post.getImageUrl();
        this.likesCount = post.getLikeCount();

        if (post.getComments() != null) {
            this.comments = post.getComments().stream()
                    .map(CommentDTO::new)
                    .collect(Collectors.toList());
        }

    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

}
