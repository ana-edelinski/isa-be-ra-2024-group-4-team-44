package rs.ac.uns.ftn.informatika.jpa.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import rs.ac.uns.ftn.informatika.jpa.model.Post;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@JsonIgnoreProperties(ignoreUnknown = true)
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
    private Double locationLatitude;
    private Double locationLongitude;
    private List<CommentDTO> comments;
    private int likeCount;
    private boolean advertised;


    public PostDTO() {}

    public PostDTO(Integer id, Integer creatorId, String creatorUsername, String description,
                   LocalDateTime creationTime, String imagePath, Integer locationId,
                   String locationStreet, String locationCity, String locationPostalCode, List<CommentDTO> comments, int likeCount,
                    Double locationLatitude, Double locationLongitude, boolean advertised) {
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
        this.locationLatitude = locationLatitude;
        this.locationLongitude = locationLongitude;
        this.advertised = advertised;
    }

    public PostDTO(Post post) {
        this.id = post.getId();
        this.creatorId = post.getCreator().getId();
        this.creatorUsername = post.getCreator().getUsername();
        this.description = post.getDescription();
        this.creationTime = post.getCreationTime();
        this.imagePath = post.getImagePath();
        this.advertised = post.isAdvertised();

        if (post.getLocation() != null) {
            this.locationId = post.getLocation().getId();
            this.locationStreet = post.getLocation().getStreet();
            this.locationCity = post.getLocation().getCity();
            this.locationPostalCode = post.getLocation().getPostalCode();
            this.locationLatitude = post.getLocation().getLatitude();
            this.locationLongitude = post.getLocation().getLongitude();
        }

        this.likeCount = post.getLikes().size();
        this.comments = post.getComments().stream()
                .map(CommentDTO::new)
                .collect(Collectors.toList());
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

    public Double getLocationLatitude() {
        return locationLatitude;
    }
    public Double getLocationLongitude() {
        return locationLongitude;
    }

    public boolean isAdvertised() {
        return advertised;
    }
}
