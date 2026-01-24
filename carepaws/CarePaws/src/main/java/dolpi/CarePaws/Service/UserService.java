package dolpi.CarePaws.Service;

import dolpi.CarePaws.DTO.UserDTO;
import dolpi.CarePaws.Entity.UserEntity;
import dolpi.CarePaws.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //This Is Create new User
    public String craetenewuser(UserDTO userDTO){
        UserEntity userEntity=new UserEntity();
        String encodepassword=passwordEncoder.encode(userDTO.getPassword());

        userEntity.setPassword(encodepassword);
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setRoles(Collections.singletonList("USER"));

        userRepository.save(userEntity);

        return "User is Succesfully Register";
    }
}
