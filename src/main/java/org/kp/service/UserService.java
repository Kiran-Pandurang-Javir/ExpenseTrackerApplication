package org.kp.service;
import jakarta.persistence.EntityNotFoundException;
import org.kp.entity.User;

import org.kp.exception.UserNotFoundException;
import org.kp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
    User savedUser = userRepository.save(user);
    return savedUser;
    }

    public User getUser(Long id){

        User user = userRepository.findById(id).orElseThrow(()-> new UnsupportedOperationException("User not Found"));

        if(!user.isActive()){
            throw new UserNotFoundException("Reach to Admin for Activation");
        }
        return user;
    }

    public User updateUser(Long id, User updatedUser){
        User user = userRepository.findById(id).get();

    if(updatedUser.getName()!=null && !updatedUser.getName().isEmpty()){
        user.setName(updatedUser.getName());
    }

    if(updatedUser.getPassword()!=null && !updatedUser.getPassword().isEmpty()){
        user.setPassword(updatedUser.getPassword());
    }
    return userRepository.save(user);
    }

    public String deactivateUser(Long id){

        User user = userRepository.findById(id).get();
        if(user.isActive() == true){
            boolean active = false;
           user.setActive(active);
        }
        userRepository.save(user);
        return "Reach to Admin for Activation";
    }

    public String activationCheck(){
    return "Reach to Admin for Activation";
    }
}
