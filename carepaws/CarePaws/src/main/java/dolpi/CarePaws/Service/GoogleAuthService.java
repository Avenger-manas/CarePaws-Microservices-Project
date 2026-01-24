package dolpi.CarePaws.Service;

import dolpi.CarePaws.Entity.Muncipalcopration;
import dolpi.CarePaws.Entity.NgoEntity;
import dolpi.CarePaws.Entity.UserEntity;
import dolpi.CarePaws.ExceptionHandler.ResourcesNotFound;
import dolpi.CarePaws.JwtToken.JwtUtil;
import dolpi.CarePaws.Repository.MuncipalRepository;
import dolpi.CarePaws.Repository.NgoRepository;
import dolpi.CarePaws.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@Service
public class GoogleAuthService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MyUserDetails myUserDetails;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private NgoRepository ngoRepository;

    @Autowired
    private MuncipalRepository muncipalRepository;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    //craete new token from oath2
    public String handleGoogleCallback(String code,String check) {

        String tokenEndpoint = "https://oauth2.googleapis.com/token";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", "https://developers.google.com/oauthplayground");
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<>(params, headers);


            ResponseEntity<Map> tokenResponse =
                    restTemplate.postForEntity(tokenEndpoint, request, Map.class);
            if (!tokenResponse.getStatusCode().is2xxSuccessful()
                    || tokenResponse.getBody() == null) {
                throw new RuntimeException("Failed to fetch Google token");
            }

            String idToken = (String) tokenResponse.getBody().get("id_token");
            String userInfoUrl =
                    "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken;

            ResponseEntity<Map> userInfoResponse =
                    restTemplate.getForEntity(userInfoUrl, Map.class);

        String email = (String) userInfoResponse.getBody().get("email");

            if (!userInfoResponse.getStatusCode().is2xxSuccessful()
                    || userInfoResponse.getBody() == null) {
                throw new ResourcesNotFound( "Invalid Google user");
            }



            UserEntity user = userRepository.findByUsername(email);
            NgoEntity ngoEntity=ngoRepository.findByUsername(email);
            Muncipalcopration muncipalcopration=muncipalRepository.findByUsername(email);

            if (user == null && ngoEntity==null && muncipalcopration==null) {
                if(check.equals("user")){
                    user = createUser(email);
                }
                if(check.equals("ngo")){
                    ngoEntity = createngo(email);
                }

                if(check.equals("muncipal")){
                    muncipalcopration = createmuncipal(email);
                }
            }

            UserDetails userDetails = myUserDetails.loadUserByUsername(email);

            return jwtUtil.generateToken(userDetails);

    }

    //create muncipal user
    private Muncipalcopration createmuncipal(String email){
        //check email base
        if(userRepository.existsByEmail(email)) throw new ResourcesNotFound( "User Is Already Exits");
        if(ngoRepository.existsByEmail(email)) throw new ResourcesNotFound("User is Already Exits");
        if(muncipalRepository.existsByEmail(email)) throw new ResourcesNotFound("User is Already Exits");

        //check user base
        if(userRepository.existsByUsername(email)) throw new ResourcesNotFound( "User Is Already Exits");
        if(ngoRepository.existsByUsername(email)) throw new ResourcesNotFound("User is Already Exits");
        if(muncipalRepository.existsByUsername(email)) throw new ResourcesNotFound("User is Already Exits");

        Muncipalcopration user = new Muncipalcopration();
        user.setEmail(email);
        user.setUsername(email);
        user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
        user.setRoles(List.of("MUNCIPAL"));
        return muncipalRepository.save(user);
    }

    //create ngo user
    private NgoEntity createngo(String email){
        //check email base
        if(userRepository.existsByEmail(email)) throw new ResourcesNotFound( "User Is Already Exits");
        if(ngoRepository.existsByEmail(email)) throw new ResourcesNotFound("User is Already Exits");
        if(muncipalRepository.existsByEmail(email)) throw new ResourcesNotFound("User is Already Exits");

        //check user base
        if(userRepository.existsByUsername(email)) throw new ResourcesNotFound( "User Is Already Exits");
        if(ngoRepository.existsByUsername(email)) throw new ResourcesNotFound("User is Already Exits");
        if(muncipalRepository.existsByUsername(email)) throw new ResourcesNotFound("User is Already Exits");

        NgoEntity user = new NgoEntity();
        user.setEmail(email);
        user.setUsername(email);
        user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
        user.setRoles(List.of("NGO"));
        return ngoRepository.save(user);
    }

    //create user
    private UserEntity createUser(String email) {
        //check email base
        if(userRepository.existsByEmail(email)) throw new ResourcesNotFound( "User Is Already Exits");
        if(ngoRepository.existsByEmail(email)) throw new ResourcesNotFound("User is Already Exits");
        if(muncipalRepository.existsByEmail(email)) throw new ResourcesNotFound("User is Already Exits");

        //check user base
        if(userRepository.existsByUsername(email)) throw new ResourcesNotFound( "User Is Already Exits");
        if(ngoRepository.existsByUsername(email)) throw new ResourcesNotFound("User is Already Exits");
        if(muncipalRepository.existsByUsername(email)) throw new ResourcesNotFound("User is Already Exits");

        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setUsername(email);
        user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
        user.setRoles(List.of("USER"));
        return userRepository.save(user);
    }


}
