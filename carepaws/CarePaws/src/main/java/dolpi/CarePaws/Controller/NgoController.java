package dolpi.CarePaws.Controller;

import dolpi.CarePaws.DTO.NgoDto;
import dolpi.CarePaws.DTO.NgoLoginDto;
import dolpi.CarePaws.Entity.NgoEntity;
import dolpi.CarePaws.Entity.UserEntity;
import dolpi.CarePaws.JwtToken.JwtUtil;
import dolpi.CarePaws.Repository.MuncipalRepository;
import dolpi.CarePaws.Repository.NgoRepository;
import dolpi.CarePaws.Repository.UserRepository;
import dolpi.CarePaws.Service.MyUserDetails;
import dolpi.CarePaws.Service.NgoService;
import dolpi.CarePaws.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;

@RestController
@RequestMapping("/ngo")
public class NgoController {
    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private NgoService ngoService;

    @Autowired
    private MyUserDetails myUserDetails;

    @Autowired
    private NgoRepository ngoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MuncipalRepository muncipalRepository;
    

    @Autowired
    private JwtUtil jwtUtil;


    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    @PostMapping("/signup")
    public ResponseEntity<?> ngosignup(@RequestBody NgoDto ngoDto) {

        if (ngoRepository.existsByUsername(ngoDto.getUsername())) {
            return new ResponseEntity<>("Ngo username is  Alraedy Exits", HttpStatus.CONFLICT);
        }

        //check username
        if(userRepository.existsByUsername(ngoDto.getUsername())){
            return new ResponseEntity<>("This Usename Please try Uniq Username", HttpStatus.CONFLICT);
        }

        //check muncipal username
        if(muncipalRepository.existsByUsername(ngoDto.getUsername())){
            return new ResponseEntity<>("This Usename Please try Uniq Username", HttpStatus.CONFLICT);
        }

        //check email structure
        boolean check = pattern.matcher(ngoDto.getEmail()).matches();

        if (!check)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ngo Email Incorrect Please Enter Valid Email");

        //check exits email
        if(muncipalRepository.existsByEmail(ngoDto.getEmail())){
            return new ResponseEntity<>("This User is Aleardy Exits", HttpStatus.CONFLICT);
        }

        if(ngoRepository.existsByEmail(ngoDto.getEmail())){
            return new ResponseEntity<>("This User is Aleardy Exits", HttpStatus.CONFLICT);
        }

        if(userRepository.existsByEmail(ngoDto.getEmail())){
            return new ResponseEntity<>("This User is Aleardy Exits", HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(ngoService.craetenewngo(ngoDto), HttpStatus.CREATED);
    }

    //login ngo
    @PostMapping("/login")
    public ResponseEntity<?> loginngo(@RequestBody NgoLoginDto ngoLoginDto){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(ngoLoginDto.getUsername(), ngoLoginDto.getPassword()));

            UserDetails userDetails = myUserDetails.loadUserByUsername(ngoLoginDto.getUsername());

            String token=jwtUtil.generateToken(userDetails);

            return new ResponseEntity<>(token,HttpStatus.CREATED);

        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}
