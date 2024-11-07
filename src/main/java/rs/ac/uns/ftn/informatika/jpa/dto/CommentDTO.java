package rs.ac.uns.ftn.informatika.jpa.dto;

import rs.ac.uns.ftn.informatika.jpa.model.Comment;
import java.time.LocalDateTime;

public class CommentDTO {

    private Integer id;
    private String text;
    private LocalDateTime creationTime;
    private Integer userId;
    private String username;
    private Integer postId;

    public CommentDTO() {}

    public CommentDTO(Integer id, String text, LocalDateTime creationTime, Integer userId, String username, Integer postId) {
        this.id = id;
        this.text = text;
        this.creationTime = creationTime;
        this.userId = userId;
        this.username = username;
        this.postId = postId;
    }

    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
        this.creationTime = comment.getCreationTime();
        this.userId = comment.getUser().getId();
        this.username = comment.getUser().getUsername();
        this.postId = comment.getPost().getId();
    }

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
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
