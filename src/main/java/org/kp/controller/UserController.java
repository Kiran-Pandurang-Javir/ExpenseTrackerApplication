package org.kp.controller;

import org.kp.entity.User;
import org.kp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}
