package org.kp.controller;
import org.kp.dto.UserDTO;
import org.kp.entity.User;
import org.kp.exception.ResouseNotFoundException;
import org.kp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users/")
public class UserController {

    @Autowired
    private UserService userService;
    public UserController(UserService userService){
    this.userService=userService;
    }

    @PostMapping("/register")
    public User saveUser(@RequestBody User user){
        User savedUser = userService.saveUser(user);
        return savedUser;
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody User user){

        if(user.getEmail()==null || user.getPassword()== null){
        return ResponseEntity.badRequest().body(Map.of("message", "Email and passowerd are required"));
        }

    try {
        UserDTO userDTO = userService.userLogin(user.getEmail() , user.getPassword());
        return ResponseEntity.ok(userDTO);
    }
    catch (ResouseNotFoundException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message",ex.getMessage()));
        }
    catch(Exception err){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message",err.getMessage()));
        }
    }

    @GetMapping("/fetch/{id}")
    public UserDTO getUser(@PathVariable Long id){
       UserDTO userDTO =  userService.getUser(id);
        return userDTO;
    }

    @PutMapping("/update/{id}")

    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user){
        if(id == 0 && id== null){


        }        String message = userService.updateUser(id, user);
        if(message.contains("successfully")){
            return ResponseEntity.status(HttpStatus.CREATED.body(message, "User Updated Sucessfully"));
        }
        return ResponseEntity.badRequest(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/deactivate/{id}")
    public String deactivateUser(@PathVariable Long id){

        return userService.deactivateUser(id);
    }




}
