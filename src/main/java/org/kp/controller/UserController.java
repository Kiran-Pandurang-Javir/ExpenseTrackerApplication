package org.kp.controller;

import org.kp.dto.UserDTO;
import org.kp.entity.User;
import org.kp.exception.ResourceNotFoundException;
import org.kp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody User user) {
        if (user.getEmail() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email and password are required"));
        }

        try {
            Map<String, Object> response = userService.userLogin(user.getEmail(), user.getPassword());
            return ResponseEntity.ok(response); // Return user and token
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", ex.getMessage()));
        } catch (Exception err) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", err.getMessage()));
        }
    }

    @GetMapping("/fetch/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        if (id == 0 || id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid User id");
        }
        String message = userService.updateUser(id, user);
        if (message.contains("successfully")) {
            return ResponseEntity.status(HttpStatus.CREATED).body("User Updated Successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Update Failed");
    }

    @DeleteMapping("/deactivate/{id}")
    public String deactivateUser(@PathVariable Long id) {
        return userService.deactivateUser(id);
    }
}