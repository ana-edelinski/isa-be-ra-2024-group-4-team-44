package com.example.advertisingreceiver.dto;

import java.time.LocalDateTime;

public class AdvertisingMessageDTO {

    private String description;
    private LocalDateTime creationTime;
    private String creatorUsername;

    // Constructors
    public AdvertisingMessageDTO() {
    }

    public AdvertisingMessageDTO(String description, LocalDateTime creationTime, String creatorUsername) {
        this.description = description;
        this.creationTime = creationTime;
        this.creatorUsername = creatorUsername;
    }

    // Getters and setters
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

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public void setCreatorUsername(String creatorUsername) {
        this.creatorUsername = creatorUsername;
    }

    @Override
    public String toString() {
        return "AdvertisingMessageDTO{" +
                "description='" + description + '\'' +
                ", creationTime=" + creationTime +
                ", creatorUsername='" + creatorUsername + '\'' +
                '}';
    }



}
