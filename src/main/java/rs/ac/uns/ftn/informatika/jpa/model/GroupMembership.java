package rs.ac.uns.ftn.informatika.jpa.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class GroupMembership {
    @Id
    @GeneratedValue
    private Long id;

    private Integer userId;
    private Integer groupId;
    private LocalDateTime joinedAt;
    private boolean seededHistory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public boolean isSeededHistory() {
        return seededHistory;
    }

    public void setSeededHistory(boolean seededHistory) {
        this.seededHistory = seededHistory;
    }
}

