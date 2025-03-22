package org.kp.service;

import org.kp.dto.UserDTO;
import org.kp.entity.User;
import org.kp.exception.ResourceNotFoundException;
import org.kp.exception.UserNotFoundException;
import org.kp.repository.UserRepository;
import org.kp.security.JwtUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils; // Inject JwtUtils

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils; // Initialize JwtUtils
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    public UserDTO getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not Found"));
        if (!user.isActive()) {
            throw new UserNotFoundException("Reach to Admin for Activation");
        }
        UserDTO userDTO = new UserDTO(user.getName(), user.getEmail());
        return userDTO;
    }

    public Map<String, Object> userLogin(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Invalid Email and Password"));
        if (!user.isActive()) {
            throw new ResourceNotFoundException("User is Inactive. Please contact Admin");
        }

        if (passwordEncoder.matches(password, user.getPassword())) {
            // Generate JWT token
            String token = jwtUtils.generateToken(user.getEmail());

            // Return UserDTO and token
            UserDTO userDTO = new UserDTO(user.getName(), user.getEmail());
            return Map.of("user", userDTO, "token", token);
        }
        throw new ResourceNotFoundException("Invalid Password, Try Again");
    }

    public String updateUser(Long id, User updatedUser) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found with id " + id));
        if (!user.isActive()) {
            throw new UserNotFoundException("User Id :" + id + " is Deactivated. Reach out to admin");
        }
        if (updatedUser.getName() != null && !updatedUser.getName().isEmpty()) {
            user.setName(updatedUser.getName());
        }
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        userRepository.save(user);
        return "User Updated Successfully";
    }

    public String deactivateUser(Long id) {
        User user = userRepository.findById(id).get();
        if (user.isActive()) {
            user.setActive(false);
        }
        userRepository.save(user);
        return "Reach to Admin for Activation";
    }

    public String activationCheck() {
        return "Reach to Admin for Activation";
    }
}