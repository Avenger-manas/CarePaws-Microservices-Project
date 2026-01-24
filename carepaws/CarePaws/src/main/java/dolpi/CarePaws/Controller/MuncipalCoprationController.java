package dolpi.CarePaws.Controller;

import dolpi.CarePaws.DTO.MuncipalDTO;
import dolpi.CarePaws.DTO.muncipallogin;
import dolpi.CarePaws.Entity.Muncipalcopration;
import dolpi.CarePaws.JwtToken.JwtUtil;
import dolpi.CarePaws.Repository.MuncipalRepository;
import dolpi.CarePaws.Repository.NgoRepository;
import dolpi.CarePaws.Repository.UserRepository;
import dolpi.CarePaws.Service.MuncipalService;
import dolpi.CarePaws.Service.MyUserDetails;
import dolpi.CarePaws.Service.NgoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@RestController
@RequestMapping("/muncipal")
public class MuncipalCoprationController {
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
    private MuncipalService muncipalService;

    @Autowired
    private MuncipalRepository muncipalRepository;


    @Autowired
    private JwtUtil jwtUtil;


    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    @PostMapping("/signup")
    public ResponseEntity<?> signupmuncipal(@RequestBody MuncipalDTO muncipalDTO) {
        if (muncipalRepository.existsByUsername(muncipalDTO.getUsername())) {
            return new ResponseEntity<>("Username Is Aleardy Try Uniq Username", HttpStatus.CONFLICT);
        }

        //check username
        if (userRepository.existsByUsername(muncipalDTO.getUsername())) {
            return new ResponseEntity<>("This Username Please try Uniq Username", HttpStatus.CONFLICT);
        }

        //check username ngo
        if (ngoRepository.existsByUsername(muncipalDTO.getUsername())) {
            return new ResponseEntity<>("This Username Please try Uniq Username", HttpStatus.CONFLICT);
        }


        //check email structure
        boolean check = pattern.matcher(muncipalDTO.getEmail()).matches();

        if (!check) return ResponseEntity.status(HttpStatus.CONFLICT).body("Ngo Email Incorrect Please Enter Valid Email");

        //check exits email
        if(muncipalRepository.existsByEmail(muncipalDTO.getEmail())){
            return new ResponseEntity<>("This User is Aleardy Exits", HttpStatus.CONFLICT);
        }

        if(ngoRepository.existsByEmail(muncipalDTO.getEmail())){
            return new ResponseEntity<>("This User is Aleardy Exits", HttpStatus.CONFLICT);
        }

        if(userRepository.existsByEmail(muncipalDTO.getEmail())){
            return new ResponseEntity<>("This User is Aleardy Exits", HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(muncipalService.craetenewmuncipal(muncipalDTO),HttpStatus.OK);

   }

    //login muncipal
    @PostMapping("/login")
   public ResponseEntity<?> loginmuncipal(@RequestBody muncipallogin muncipalloginuser){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(muncipalloginuser.getUsername(), muncipalloginuser.getPassword()));

            UserDetails userDetails = myUserDetails.loadUserByUsername(muncipalloginuser.getUsername());

            String token=jwtUtil.generateToken(userDetails);

            return new ResponseEntity<>(token,HttpStatus.CREATED);

        }catch (Exception e){
            throw new RuntimeException(e);
        }
   }





}
