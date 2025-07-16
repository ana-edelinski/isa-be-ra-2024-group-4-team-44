package rs.ac.uns.ftn.informatika.jpa.dto;
import java.time.LocalDateTime;

public class AdvertisingMessageDTO {

    private String description;
    private LocalDateTime creationTime;
    private String username;

    // Constructors
    public AdvertisingMessageDTO() {}

    public AdvertisingMessageDTO(String description, LocalDateTime creationTime, String username) {
        this.description = description;
        this.creationTime = creationTime;
        this.username = username;
    }

    // Getters and Setters
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getCreationTime() { return creationTime; }
    public void setCreationTime(LocalDateTime creationTime) { this.creationTime = creationTime; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}
