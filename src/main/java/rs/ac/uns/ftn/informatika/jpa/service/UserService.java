package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.informatika.jpa.model.User;
import rs.ac.uns.ftn.informatika.jpa.model.Address;
import rs.ac.uns.ftn.informatika.jpa.dto.UserDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.ChangePasswordDTO;
import rs.ac.uns.ftn.informatika.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    private EmailService emailService;

    @Transactional
    public ResponseEntity<?> register(UserDTO userDto) {
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("Passwords do not match!");
        }
        if (userDto.getUsername() == null || userDto.getUsername().isEmpty()) {
            return ResponseEntity.badRequest().body("Username is required!");
        }
        if (userDto.getEmail() == null || userDto.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("Email address is required!");
        }
        if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body("Password is required!");
        }
        if (userRepository.existsByUsername(userDto.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists!");
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            return ResponseEntity.badRequest().body("Email address already exists!");
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setActivated(false);
        user.setEnabled(true);

        String activationToken = UUID.randomUUID().toString();
        user.setActivationToken(activationToken);

        Address address = new Address();
        address.setStreet(userDto.getStreet());
        address.setCity(userDto.getCity());
        address.setPostalCode(userDto.getPostalCode());
        user.setAddress(address);

        userRepository.save(user);

        emailService.sendActivationEmail(user.getEmail(), activationToken);

        return ResponseEntity.status(HttpStatus.CREATED).body(new UserDTO(user));
    }


    public ResponseEntity<?> login(UserDTO userDto) {
        Optional<User> userOptional = userRepository.findByEmail(userDto.getEmail());
        if (!userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Ne postoji korisnik sa tim email-om.");
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Neispravna lozinka");
        }

        if (!user.isActivated()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Nalog nije aktiviran");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("user", new UserDTO(user));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> activateUser(String token) {
        Optional<User> userOptional = userRepository.findByActivationToken(token);
        if (!userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nevažeći aktivacioni token.");
        }

        User user = userOptional.get();
        user.setActivated(true);
        user.setActivationToken(null);

        userRepository.save(user);

        return ResponseEntity.ok("Korisnik je uspešno aktiviran.");
    }

    public boolean changePassword(Integer userId, ChangePasswordDTO changePasswordDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));

        if (!passwordEncoder.matches(changePasswordDTO.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmPassword())) {
            throw new IllegalArgumentException("New passwords do not match");
        }

        user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        userRepository.save(user);

        return true;
    }

    public UserDTO getUserProfile(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        return new UserDTO(user);
    }

    public User updateUserProfile(Integer userId, UserDTO userProfileUpdateDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        user.setName(userProfileUpdateDto.getName());
        user.setSurname(userProfileUpdateDto.getSurname());

        Address address = user.getAddress();
        if (address == null) {
            address = new Address();
        }

        address.setStreet(userProfileUpdateDto.getStreet());
        address.setCity(userProfileUpdateDto.getCity());
        address.setPostalCode(userProfileUpdateDto.getPostalCode());

        user.setAddress(address);

        return userRepository.save(user);
    }

}
