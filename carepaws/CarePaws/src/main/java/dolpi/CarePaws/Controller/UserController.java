package dolpi.CarePaws.Controller;

import dolpi.CarePaws.DTO.UserDTO;
import dolpi.CarePaws.DTO.UserloginDto;
import dolpi.CarePaws.Entity.UserEntity;
import dolpi.CarePaws.JwtToken.JwtUtil;
import dolpi.CarePaws.Repository.MuncipalRepository;
import dolpi.CarePaws.Repository.NgoRepository;
import dolpi.CarePaws.Repository.UserRepository;
import dolpi.CarePaws.Service.MyUserDetails;
import dolpi.CarePaws.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";


    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private NgoRepository ngoRepository;

    @Autowired
    private MyUserDetails myUserDetails;

    @Autowired
    private MuncipalRepository muncipalRepository;

    @Autowired
    private JwtUtil jwtUtil;


    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    //signup new user
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserDTO userDTO){
        if(userRepository.existsByUsername(userDTO.getUsername())){
            return new ResponseEntity<>("User is username Alraedy Exits",HttpStatus.CONFLICT);
        }

        //check username ngo
        if(ngoRepository.existsByUsername(userDTO.getUsername())){
            return new ResponseEntity<>("User is username Alraedy Exits Please Try Uniq Username",HttpStatus.CONFLICT);
        }
        
        //check user email
        if(userRepository.existsByEmail(userDTO.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This User is Already Exits");
        }

        //check muncipal username
        if(muncipalRepository.existsByUsername(userDTO.getUsername())){
            return new ResponseEntity<>("This Usename Please try Uniq Username", HttpStatus.CONFLICT);
        }

        //check email structure
        boolean check = pattern.matcher(userDTO.getEmail()).matches();

        if (!check)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User Email Incorrect Please Enter Valid Email");

        //check exits email
        if(muncipalRepository.existsByEmail(userDTO.getEmail())){
            return new ResponseEntity<>("This User is Aleardy Exits", HttpStatus.CONFLICT);
        }

        if(ngoRepository.existsByEmail(userDTO.getEmail())){
            return new ResponseEntity<>("This User is Aleardy Exits", HttpStatus.CONFLICT);
        }

        if(userRepository.existsByEmail(userDTO.getEmail())){
            return new ResponseEntity<>("This User is Aleardy Exits", HttpStatus.CONFLICT);
        }


        return new ResponseEntity<>(userService.craetenewuser(userDTO),HttpStatus.CREATED);


    }

    //login username
     @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserloginDto userloginDto){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userloginDto.getUsername(), userloginDto.getPassword()));

            UserDetails userDetails = myUserDetails.loadUserByUsername(userloginDto.getUsername());

            //token generate
            String token=jwtUtil.generateToken(userDetails);

            return new ResponseEntity<>(token,HttpStatus.CREATED);

        }catch (Exception e){
            throw new RuntimeException(e);
        }


     }
}
