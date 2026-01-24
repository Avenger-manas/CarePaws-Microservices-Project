package dolpi.CarePaws.Service;

import dolpi.CarePaws.Entity.UserEntity;
import dolpi.CarePaws.JwtToken.JwtUtil;
import dolpi.CarePaws.Repository.MuncipalRepository;
import dolpi.CarePaws.Repository.NgoRepository;
import dolpi.CarePaws.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GoogleAuthServiceTest {
    @InjectMocks
    private GoogleAuthService googleAuthService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private MyUserDetails myUserDetails;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private NgoRepository ngoRepository;

    @Mock
    private MuncipalRepository muncipalRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Test
    void handleGoogleCallback_newUser_success() {

        // ---------- MOCK GOOGLE TOKEN RESPONSE ----------
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("id_token", "fake-id-token");

        ResponseEntity<Map> tokenResponse =
                new ResponseEntity<>(tokenMap, HttpStatus.OK);

        when(restTemplate.postForEntity(
                contains("oauth2.googleapis.com/token"),
                any(),
                eq(Map.class)
        )).thenReturn(tokenResponse);

        // ---------- MOCK GOOGLE USER INFO ----------
        Map<String, Object> userInfoMap = new HashMap<>();
        userInfoMap.put("email", "test@gmail.com");

        ResponseEntity<Map> userInfoResponse =
                new ResponseEntity<>(userInfoMap, HttpStatus.OK);

        when(restTemplate.getForEntity(
                contains("tokeninfo"),
                eq(Map.class)
        )).thenReturn(userInfoResponse);

        // ---------- MOCK DB ----------
        when(userRepository.findByUsername("test@gmail.com")).thenReturn(null);
        when(ngoRepository.findByUsername("test@gmail.com")).thenReturn(null);
        when(muncipalRepository.findByUsername("test@gmail.com")).thenReturn(null);

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(ngoRepository.existsByEmail(anyString())).thenReturn(false);
        when(muncipalRepository.existsByEmail(anyString())).thenReturn(false);

        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(ngoRepository.existsByUsername(anyString())).thenReturn(false);
        when(muncipalRepository.existsByUsername(anyString())).thenReturn(false);

        when(passwordEncoder.encode(anyString())).thenReturn("encoded-pass");

        when(userRepository.save(any(UserEntity.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        // ---------- MOCK USER DETAILS + JWT ----------
        UserDetails userDetails =
                new User("test@gmail.com", "pass", new ArrayList<>());

        when(myUserDetails.loadUserByUsername("test@gmail.com"))
                .thenReturn(userDetails);

        when(jwtUtil.generateToken(userDetails))
                .thenReturn("mock-jwt-token");

        // ---------- CALL METHOD ----------
        String token = googleAuthService
                .handleGoogleCallback("dummy-code", "user");

        // ---------- VERIFY ----------
        assertEquals("mock-jwt-token", token);
    }
}
