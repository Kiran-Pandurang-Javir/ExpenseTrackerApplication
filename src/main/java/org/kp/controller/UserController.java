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

    @GetMapping("/fetch/{id}")
    public User getUser(@PathVariable Long id){
       User user =  userService.getUser(id);
        return user;
    }

    @PutMapping("/update/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user){
        User updatedUser = userService.updateUser(id, user);
        return updatedUser;
    }

    @DeleteMapping("/deactivate/{id}")
    public String deactivateUser(@PathVariable Long id){

        return userService.deactivateUser(id);
    }




}
