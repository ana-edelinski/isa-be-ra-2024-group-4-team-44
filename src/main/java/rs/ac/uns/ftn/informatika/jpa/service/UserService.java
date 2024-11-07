package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.informatika.jpa.model.User;
import rs.ac.uns.ftn.informatika.jpa.dto.UserDTO;
import rs.ac.uns.ftn.informatika.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    private EmailService emailService;

    @Transactional
    public ResponseEntity<?> register(UserDTO userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setName(userDto.getName());
        user.setActivated(false);

        String activationToken = UUID.randomUUID().toString();
        user.setActivationToken(activationToken);

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
}
