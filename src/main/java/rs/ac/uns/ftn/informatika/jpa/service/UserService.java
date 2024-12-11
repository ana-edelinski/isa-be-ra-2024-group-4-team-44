package rs.ac.uns.ftn.informatika.jpa.service;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import rs.ac.uns.ftn.informatika.jpa.dto.UserInfoDTO;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import rs.ac.uns.ftn.informatika.jpa.dto.JwtAuthenticationRequest;
import rs.ac.uns.ftn.informatika.jpa.dto.UserTokenState;
import rs.ac.uns.ftn.informatika.jpa.model.Role;
import rs.ac.uns.ftn.informatika.jpa.model.User;
import rs.ac.uns.ftn.informatika.jpa.model.Address;
import rs.ac.uns.ftn.informatika.jpa.dto.UserDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.ChangePasswordDTO;
import rs.ac.uns.ftn.informatika.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.informatika.jpa.service.UserService;
import java.util.*;
import org.springframework.security.authentication.AuthenticationManager;
import rs.ac.uns.ftn.informatika.jpa.util.TokenUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private HttpServletRequest request; // Autowired HttpServletRequest


    @Autowired
    private RoleService roleService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenUtils tokenUtils, AuthenticationManager authenticationManager) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenUtils = tokenUtils;
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    private EmailService emailService;

    @Transactional
    public synchronized ResponseEntity<?> register(UserDTO userDto) {
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

        // Simulacija kašnjenja (za testiranje konkurencije)
        try {
            Thread.sleep(5000); // Simulirajte kašnjenje
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
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

        List<Role> roles = roleService.findByName("ROLE_USER");
        user.setRoles(roles);

        //userRepository.save(user);
        try {
            // Save user in the database
            userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            // Handle constraint violations (for example, duplicate username or email)
            if (ex.getMessage().contains("uk_6dotkott2kjsp8vw4d0m25fb7")) {
                // Provide specific error message when username already exists
                return ResponseEntity.badRequest().body("Username already exists!");
            }
            // Return a general error message for other violations
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during registration.");
        }


        emailService.sendActivationEmail(user.getEmail(), activationToken);

        return ResponseEntity.status(HttpStatus.CREATED).body(new UserDTO(user));
    }

public ResponseEntity<UserTokenState> login(
        @RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) {
    // Ukoliko kredencijali nisu ispravni, logovanje nece biti uspesno, desice se
    // AuthenticationException
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            authenticationRequest.getUsername(), authenticationRequest.getPassword()));

    // Ukoliko je autentifikacija uspesna, ubaci korisnika u trenutni security
    // kontekst
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // Kreiraj token za tog korisnika
    User user = (User) authentication.getPrincipal();
    String jwt = tokenUtils.generateToken(user.getUsername());
    int expiresIn = tokenUtils.getExpiredIn();

    // Vrati token kao odgovor na uspesnu autentifikaciju
    return ResponseEntity.ok(new UserTokenState(jwt, expiresIn, user.getId()));
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

    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return user;
        }
    }

    public List<UserInfoDTO> getAllUsersInfo() {
        List<User> users = userRepository.findAll();
        List<UserInfoDTO> userDTOs = new ArrayList<>();

        for (User user : users) {
            Integer numberOfPosts = user.getPosts().size();
            Integer numberOfFollowing = user.getFollowing().size();
            UserInfoDTO userDTO = new UserInfoDTO(user.getId(), user.getUsername(), user.getName(), user.getSurname(), user.getEmail(), numberOfPosts, numberOfFollowing);
            userDTOs.add(userDTO);
        }

        return userDTOs;
    }


    public List<UserInfoDTO> searchUsers(String name, String surname, String email, Integer minPosts, Integer maxPosts) {
        // Osiguraj da su parametri prazni stringovi ako nisu prosleđeni
        name = (name != null && !name.isEmpty()) ? name : "";
        surname = (surname != null && !surname.isEmpty()) ? surname : "";
        email = (email != null && !email.isEmpty()) ? email : "";
        minPosts = (minPosts != null && minPosts > 0) ? minPosts : 0;
        maxPosts = (maxPosts != null && maxPosts > 0) ? maxPosts : Integer.MAX_VALUE; 

        List<User> users = userRepository.searchUsers(name, surname, email, minPosts, maxPosts);
        List<UserInfoDTO> userDTOs = new ArrayList<>();

        for (User user : users) {
            Integer numberOfPosts = user.getPosts().size();
            Integer numberOfFollowing = user.getFollowing().size();
            UserInfoDTO userDTO = new UserInfoDTO(user.getId(), user.getUsername(), user.getName(), user.getSurname(), user.getEmail(), numberOfPosts, numberOfFollowing);
            userDTOs.add(userDTO);
        }

        return userDTOs;
    }



    public List<UserInfoDTO> getUsersSortedByFollowingCountAsc() {
        List<User> users = userRepository.findAllSortedByFollowingCountAsc();
        List<UserInfoDTO> userDTOs = new ArrayList<>();

        for (User user : users) {
            Integer numberOfPosts = user.getPosts().size();
            Integer numberOfFollowing = user.getFollowing().size();
            UserInfoDTO userDTO = new UserInfoDTO(user.getId(), user.getUsername(), user.getName(), user.getSurname(), user.getEmail(), numberOfPosts, numberOfFollowing);
            userDTOs.add(userDTO);
        }

        return userDTOs;
    }


    public List<UserInfoDTO> getUsersSortedByFollowingCountDesc() {
        List<User> users = userRepository.findAllSortedByFollowingCountDesc();
        List<UserInfoDTO> userDTOs = new ArrayList<>();

        for (User user : users) {
            Integer numberOfPosts = user.getPosts().size();
            Integer numberOfFollowing = user.getFollowing().size();
            UserInfoDTO userDTO = new UserInfoDTO(user.getId(), user.getUsername(), user.getName(), user.getSurname(), user.getEmail(), numberOfPosts, numberOfFollowing);
            userDTOs.add(userDTO);
        }
        return userDTOs;
    }

    public List<UserInfoDTO> getUsersSortedByEmailAsc() {
        List<User> users = userRepository.findAllSortedByEmailAsc();
        List<UserInfoDTO> userDTOs = new ArrayList<>();

        for (User user : users) {
            Integer numberOfPosts = user.getPosts().size();
            Integer numberOfFollowing = user.getFollowing().size();
            UserInfoDTO userDTO = new UserInfoDTO(user.getId(), user.getUsername(), user.getName(), user.getSurname(), user.getEmail(), numberOfPosts, numberOfFollowing);
            userDTOs.add(userDTO);
        }
        return userDTOs;
    }

    public List<UserInfoDTO> getUsersSortedByEmailDesc() {
        List<User> users = userRepository.findAllSortedByEmailDesc();
        List<UserInfoDTO> userDTOs = new ArrayList<>();

        for (User user : users) {
            Integer numberOfPosts = user.getPosts().size();
            Integer numberOfFollowing = user.getFollowing().size();
            UserInfoDTO userDTO = new UserInfoDTO(user.getId(), user.getUsername(), user.getName(), user.getSurname(), user.getEmail(), numberOfPosts, numberOfFollowing);
            userDTOs.add(userDTO);
        }
        return userDTOs;
    }

    public Integer getRole(Integer userId){
        return userRepository.findRoleIdByUserId(userId);
    }

    public String getClientIP() {
        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || remoteAddr.isEmpty()) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        return remoteAddr;
    }

}
