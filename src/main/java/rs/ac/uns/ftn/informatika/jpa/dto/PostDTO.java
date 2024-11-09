package rs.ac.uns.ftn.informatika.jpa.dto;

import rs.ac.uns.ftn.informatika.jpa.model.Post;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class PostDTO {

    private Integer id;
    private Integer creatorId;
    private String creatorUsername;
    private String description;
    private LocalDateTime creationTime;
    private String imagePath;
    private Integer locationId;
    private String locationStreet;
    private String locationCity;
    private String locationPostalCode;
    private List<CommentDTO> comments;
    private int likeCount;

    public PostDTO() {}

    public PostDTO(Integer id, Integer creatorId, String creatorUsername, String description,
                   LocalDateTime creationTime, String imagePath, Integer locationId,
                   String locationStreet, String locationCity, String locationPostalCode, List<CommentDTO> comments, int likeCount) {
        this.id = id;
        this.creatorId = creatorId;
        this.creatorUsername = creatorUsername;
        this.description = description;
        this.creationTime = creationTime;
        this.imagePath = imagePath;
        this.locationId = locationId;
        this.locationStreet = locationStreet;
        this.locationCity = locationCity;
        this.locationPostalCode = locationPostalCode;
        this.comments = comments;
        this.likeCount = likeCount;
    }

    public PostDTO(Post post) {
        this.id = post.getId();
        this.creatorId = post.getCreator().getId();
        this.creatorUsername = post.getCreator().getUsername();
        this.description = post.getDescription();
        this.creationTime = post.getCreationTime();
        this.imagePath = post.getImagePath();

        if (post.getLocation() != null) {
            this.locationId = post.getLocation().getId();
            this.locationStreet = post.getLocation().getStreet();
            this.locationCity = post.getLocation().getCity();
            this.locationPostalCode = post.getLocation().getPostalCode();
        }

        this.likeCount = post.getLikes().size();
        this.comments = post.getComments().stream()
                .map(CommentDTO::new)
                .collect(Collectors.toList());
    }

    public Integer getId() {
        return id;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public String getLocationStreet() {
        return locationStreet;
    }

    public String getLocationCity() {
        return locationCity;
    }

    public String getLocationPostalCode() {
        return locationPostalCode;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public int getLikeCount() {
        return likeCount;
    }
}
