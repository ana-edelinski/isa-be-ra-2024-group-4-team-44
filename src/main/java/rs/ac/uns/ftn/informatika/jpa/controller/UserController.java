package rs.ac.uns.ftn.informatika.jpa.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.UserInfoDTO;
import rs.ac.uns.ftn.informatika.jpa.service.UserService;
import rs.ac.uns.ftn.informatika.jpa.dto.UserDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.ChangePasswordDTO;
import rs.ac.uns.ftn.informatika.jpa.model.User;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDto) {
        return userService.register(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDto) {
        return userService.login(userDto);
    }

    @GetMapping("/activate/{token}")
    public ResponseEntity<?> activateUser(@PathVariable String token) {
        return userService.activateUser(token);
    }

    @PutMapping("/{id}/changePassword")
    public ResponseEntity<?> changePassword(@PathVariable Integer id, @RequestBody ChangePasswordDTO changePasswordDTO) {
        userService.changePassword(id, changePasswordDTO);
        return ResponseEntity.ok("Password updated successfully");
    }


    @GetMapping("/{id}/profile")
    public ResponseEntity<UserDTO> getUserProfile(@PathVariable Integer id) {
        UserDTO userProfileDto = userService.getUserProfile(id);
        return ResponseEntity.ok(userProfileDto);
    }

    @PutMapping("/{id}/profile")
    public ResponseEntity<User> updateUserProfile(@PathVariable Integer id, @RequestBody UserDTO userProfileUpdateDto) {
        User updatedUser = userService.updateUserProfile(id, userProfileUpdateDto);
        return ResponseEntity.ok(updatedUser);
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
    public List<User> getUsersSortedByFollowingAsc() {
        return userService.getUsersSortedByFollowingCountAsc();
    }

    @GetMapping("/sort/following/desc")
    public List<User> getUsersSortedByFollowingDesc() {
        return userService.getUsersSortedByFollowingCountDesc();
    }

    @GetMapping("/sort/email/asc")
    public List<User> getUsersSortedByEmailAsc() {
        return userService.getUsersSortedByEmailAsc();
    }

    @GetMapping("/sort/email/desc")
    public List<User> getUsersSortedByEmailDesc() {
        return userService.getUsersSortedByEmailDesc();
    }

}
