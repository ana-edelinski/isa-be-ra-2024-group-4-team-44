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

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public ResponseEntity<User> register(UserDTO userDto) {
        // Proveri da li korisničko ime već postoji
        if (userRepository.existsByUsername(userDto.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // 409 Conflict
        }

        // Proveri da li email već postoji
        if (userRepository.existsByEmail(userDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // 409 Conflict
        }

        // Kreiraj novog korisnika
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword())); // Heširaj lozinku
        user.setActivated(false); // Postavi da nije aktiviran

        userRepository.save(user); // Spasi korisnika

        return ResponseEntity.status(HttpStatus.CREATED).body(user); // 201 Created
    }

    public ResponseEntity<?> login(UserDTO userDto) {
        User user = userRepository.findByEmail(userDto.getEmail()).orElseThrow(() ->
                new RuntimeException("Ne postoji korisnik sa tim email-om."));

        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // 401 Unauthorized
        }

        if (!user.isActivated()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null); // 403 Forbidden
        }

        return ResponseEntity.ok(user); // 200 OK
    }

    // Dodaj metodu za aktivaciju
    public ResponseEntity<?> activateUser(String token) {
        // Logika za aktivaciju naloga
        return ResponseEntity.ok("Korisnik je aktiviran."); // Primer odgovora
    }
}
