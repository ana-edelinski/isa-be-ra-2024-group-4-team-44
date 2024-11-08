package rs.ac.uns.ftn.informatika.jpa.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User creator;

    @Column(name = "description", unique = false, nullable = false)
    private String description;

    @Column(name = "creation_time", nullable = false)
    private LocalDateTime creationTime;

    @Column(name = "image_path", unique = false, nullable = false)
    private String imagePath;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Address location;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @Column(name = "like_count")
    private Integer likeCount = 0;

    public Post() {}

    public Post(Integer id, User creator, String description, LocalDateTime creationTime, String imagePath, Address location, List<Comment> comments, Integer likeCount) {
        this.id = id;
        this.creator = creator;
        this.description = description;
        this.creationTime = creationTime;
        this.imagePath = imagePath;
        this.location = location;
        this.comments = comments;
        this.likeCount = likeCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Address getLocation() {
        return location;
    }

    public void setLocation(Address location) {
        this.location = location;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }
}
