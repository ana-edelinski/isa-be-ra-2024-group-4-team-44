package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import rs.ac.uns.ftn.informatika.jpa.dto.JwtAuthenticationRequest;
import rs.ac.uns.ftn.informatika.jpa.dto.UserInfoDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.UserInfoDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.JwtAuthenticationRequest;
import rs.ac.uns.ftn.informatika.jpa.service.UserService;
import rs.ac.uns.ftn.informatika.jpa.dto.UserDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.ChangePasswordDTO;
import rs.ac.uns.ftn.informatika.jpa.model.User;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value ="/api/users", produces = MediaType.APPLICATION_JSON_VALUE)

public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDto) {
        return userService.register(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDto, HttpServletResponse response) {
        // Kreiraj JwtAuthenticationRequest sa podacima iz UserDTO
        JwtAuthenticationRequest authenticationRequest = new JwtAuthenticationRequest(userDto.getUsername(), userDto.getPassword());

        // Pozovi servisnu metodu koja prihvata JwtAuthenticationRequest
        return userService.login(authenticationRequest, response);
    }


    @GetMapping("/activate/{token}")
    public ResponseEntity<?> activateUser(@PathVariable String token) {
        return userService.activateUser(token);
    }

    @PutMapping("/{id}/changePassword")
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<?> changePassword(@PathVariable Integer id, @RequestBody ChangePasswordDTO changePasswordDTO) {
        userService.changePassword(id, changePasswordDTO);
        return ResponseEntity.ok("Password updated successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Integer id) {
        UserDTO userProfileDto = userService.getUserProfile(id);
        return ResponseEntity.ok(userProfileDto);
    }

    @GetMapping("/{id}/profile")
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<UserDTO> getUserProfile(@PathVariable Integer id) {
        UserDTO userProfileDto = userService.getUserProfile(id);
        return ResponseEntity.ok(userProfileDto);
    }

    @PutMapping("/{id}/profile")
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<User> updateUserProfile(@PathVariable Integer id, @RequestBody UserDTO userProfileUpdateDto) {
        User updatedUser = userService.updateUserProfile(id, userProfileUpdateDto);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/foo")
    public Map<String, String> getFoo() {
        Map<String, String> fooObj = new HashMap<>();
        fooObj.put("foo", "bar");
        return fooObj;
    }

    @GetMapping("/registered")
    public ResponseEntity<List<UserInfoDTO>> getAllUsersInfo() {
        List<UserInfoDTO> usersInfo = userService.getAllUsersInfo();
        return ResponseEntity.ok(usersInfo);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserInfoDTO>> searchUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String surname,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Integer minPosts,
            @RequestParam(required = false) Integer maxPosts) {
        List<UserInfoDTO> users = userService.searchUsers(name, surname, email, minPosts, maxPosts);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/sort/following/asc")
    public List<UserInfoDTO> getUsersSortedByFollowingAsc() {
        return userService.getUsersSortedByFollowingCountAsc();
    }

    @GetMapping("/sort/following/desc")
    public List<UserInfoDTO> getUsersSortedByFollowingDesc() {
        return userService.getUsersSortedByFollowingCountDesc();
    }

    @GetMapping("/sort/email/asc")
    public List<UserInfoDTO> getUsersSortedByEmailAsc() {
        return userService.getUsersSortedByEmailAsc();
    }

    @GetMapping("/sort/email/desc")
    public List<UserInfoDTO> getUsersSortedByEmailDesc() {
        return userService.getUsersSortedByEmailDesc();
    }

    @GetMapping("/role/{id}")
    public Integer getRole(@PathVariable Integer id){
        Integer role = userService.getRole(id);
        return role;
    }

}
