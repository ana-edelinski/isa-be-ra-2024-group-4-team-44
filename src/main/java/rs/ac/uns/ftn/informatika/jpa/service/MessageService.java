package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.dto.MessageDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Message;
import rs.ac.uns.ftn.informatika.jpa.model.User;
import rs.ac.uns.ftn.informatika.jpa.repository.MessageRepository;
import rs.ac.uns.ftn.informatika.jpa.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public List<MessageDTO> getChatHistory(Integer userId1, Integer userId2) {
        User user1 = userRepository.findById(userId1)
                .orElseThrow(() -> new NoSuchElementException("User not found: " + userId1));
        User user2 = userRepository.findById(userId2)
                .orElseThrow(() -> new NoSuchElementException("User not found: " + userId2));

        return messageRepository.findChatHistory(user1, user2)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<MessageDTO> getReceivedMessages(Integer receiverId) {
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new NoSuchElementException("User not found: " + receiverId));

        return messageRepository.findReceivedMessages(receiver)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<MessageDTO> getSentMessages(Integer senderId) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new NoSuchElementException("User not found: " + senderId));

        return messageRepository.findSentMessages(sender)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public MessageDTO sendMessage(Integer senderId, Integer receiverId, String content) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new NoSuchElementException("User not found: " + senderId));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new NoSuchElementException("User not found: " + receiverId));

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());

        Message savedMessage = messageRepository.save(message);
        MessageDTO messageDTO = convertToDTO(savedMessage);

        messagingTemplate.convertAndSend("/topic/messages/" + receiverId, messageDTO);

        return messageDTO;
    }

    @Transactional
    public void deleteChatHistory(Integer userId1, Integer userId2) {
        User user1 = userRepository.findById(userId1)
                .orElseThrow(() -> new NoSuchElementException("User not found: " + userId1));
        User user2 = userRepository.findById(userId2)
                .orElseThrow(() -> new NoSuchElementException("User not found: " + userId2));

        messageRepository.deleteChatHistory(user1, user2);
    }

    private MessageDTO convertToDTO(Message message) {
        return new MessageDTO(
                message.getId(),
                message.getSender().getId(),
                message.getSender().getUsername(),
                message.getReceiver().getId(),
                message.getReceiver().getUsername(),
                message.getContent(),
                message.getTimestamp()
        );
    }
}
