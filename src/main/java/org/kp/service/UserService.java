package org.kp.service;
import org.kp.dto.UserDTO;
import org.kp.entity.User;
import org.kp.exception.ResouseNotFoundException;
import org.kp.exception.UserNotFoundException;
import org.kp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository =userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    User savedUser = userRepository.save(user);
    return savedUser;
    }

    public UserDTO getUser(Long id){

        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not Found"));
        if(!user.isActive()){
            throw new UserNotFoundException("Reach to Admin for Activation");
        }
    UserDTO userDTO = new UserDTO(user.getName(),user.getEmail());

        return userDTO;
    }

    public UserDTO userLogin(String email, String password){
         User user = userRepository.findByEmail(email).orElseThrow(()-> new ResouseNotFoundException("Invalid Email and Password"));
         if(!user.isActive()){
             throw new ResouseNotFoundException("User is Incative . Please contact Admin");
         }

         if(passwordEncoder.matches(password,user.getPassword())){
    return new UserDTO(user.getName(),user.getEmail());
         }
        throw new ResouseNotFoundException("Invalid Password , Try Again");
    }

    public String updateUser(Long id, User updatedUser){

        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User Not Found with id "+id));
    if(!user.isActive()){
        throw  new UserNotFoundException("User Id :" + id +"is Deactivate reach to admin");
    }
    if(updatedUser.getName()!=null && !updatedUser.getName().isEmpty()){
        user.setName(updatedUser.getName());
    }

    if(updatedUser.getPassword()!=null && !updatedUser.getPassword().isEmpty()){
        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
    }
            userRepository.save(user);
        return "User Updated Successfully";
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
