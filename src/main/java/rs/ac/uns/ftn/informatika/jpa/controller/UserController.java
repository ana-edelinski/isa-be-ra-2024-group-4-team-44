package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.service.UserService;
import rs.ac.uns.ftn.informatika.jpa.dto.UserDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.ChangePasswordDTO;
import rs.ac.uns.ftn.informatika.jpa.model.User;

import java.util.HashMap;
import java.util.Map;

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
    @GetMapping("/foo")
    public Map<String, String> getFoo() {
        Map<String, String> fooObj = new HashMap<>();
        fooObj.put("foo", "bar");
        return fooObj;
    }
}
