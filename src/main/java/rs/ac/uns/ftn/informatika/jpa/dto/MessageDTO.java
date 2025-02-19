package rs.ac.uns.ftn.informatika.jpa.dto;

import rs.ac.uns.ftn.informatika.jpa.model.Message;
import java.time.LocalDateTime;

public class MessageDTO {

    private Integer id;
    private Integer senderId;
    private String senderUsername;
    private Integer receiverId;
    private String receiverUsername;
    private String content;
    private LocalDateTime timestamp;

    public MessageDTO() {}

    public MessageDTO(Integer id, Integer senderId, String senderUsername, Integer receiverId,
                      String receiverUsername, String content, LocalDateTime timestamp) {
        this.id = id;
        this.senderId = senderId;
        this.senderUsername = senderUsername;
        this.receiverId = receiverId;
        this.receiverUsername = receiverUsername;
        this.content = content;
        this.timestamp = timestamp;
    }

    public MessageDTO(Message message) {
        this.id = message.getId();
        this.senderId = message.getSender().getId();
        this.senderUsername = message.getSender().getUsername();
        this.receiverId = message.getReceiver().getId();
        this.receiverUsername = message.getReceiver().getUsername();
        this.content = message.getContent();
        this.timestamp = message.getTimestamp();
    }

    public Integer getId() {
        return id;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
