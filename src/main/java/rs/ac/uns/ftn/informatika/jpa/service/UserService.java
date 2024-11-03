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
    private EmailService emailService; // Klasa za slanje emailova

    @Transactional
    public ResponseEntity<?> register(UserDTO userDto) {
        // Validacija (proveri da li već postoji korisnik sa istim email-om ili korisničkim imenom)
        System.out.println ("Započeta registracija za korisnika: AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setName(userDto.getName());
        //user.setAddress(userDto.getAddress());
        user.setActivated(false);

        userRepository.save(user);

        // Generisanje aktivacionog linka
        String activationToken = UUID.randomUUID().toString();
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

        // Obmotavanje UserDTO u JSON format
        Map<String, Object> response = new HashMap<>();
        response.put("user", new UserDTO(user));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> activateUser(String token) {

        return ResponseEntity.ok("Korisnik je aktiviran.");
    }
}
