package rs.ac.uns.ftn.informatika.jpa.dto;

import rs.ac.uns.ftn.informatika.jpa.model.Comment;

public class CommentDTO {
    private Integer id;
    private String text;

    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
    }

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

}

