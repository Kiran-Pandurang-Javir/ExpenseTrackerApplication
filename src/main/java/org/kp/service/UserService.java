package org.kp.service;
import org.kp.entity.User;

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
}
