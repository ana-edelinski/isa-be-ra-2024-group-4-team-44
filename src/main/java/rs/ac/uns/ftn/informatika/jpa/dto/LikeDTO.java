package rs.ac.uns.ftn.informatika.jpa.dto;

import rs.ac.uns.ftn.informatika.jpa.model.Like;

public class LikeDTO {

    private Integer id;
    private Integer userId;
    private String username;
    private Integer postId;

    public LikeDTO() {}

    public LikeDTO(Integer id, Integer userId, String username, Integer postId) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.postId = postId;
    }

    public LikeDTO(Like like) {
        this.id = like.getId();
        this.userId = like.getUser().getId();
        this.username = like.getUser().getUsername();
        this.postId = like.getPost().getId();
    }

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public Integer getPostId() {
        return postId;
    }
}
