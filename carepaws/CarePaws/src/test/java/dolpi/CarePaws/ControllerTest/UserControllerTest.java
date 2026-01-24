package dolpi.CarePaws.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dolpi.CarePaws.DTO.UserDTO;
import dolpi.CarePaws.DTO.UserloginDto;
import dolpi.CarePaws.JwtToken.JwtUtil;
import dolpi.CarePaws.Repository.MuncipalRepository;
import dolpi.CarePaws.Repository.NgoRepository;
import dolpi.CarePaws.Repository.UserRepository;
import dolpi.CarePaws.Service.MyUserDetails;
import dolpi.CarePaws.Service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private NgoRepository ngoRepository;

    @MockBean
    private MuncipalRepository muncipalRepository;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private MyUserDetails myUserDetails;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    void signup_success() throws Exception {
        UserDTO dto = new UserDTO();
        dto.setUsername("user4");
        dto.setEmail("user4@gmail.com");
        dto.setPassword("xxx");

        Mockito.when(userRepository.existsByUsername("user4")).thenReturn(false);
        Mockito.when(ngoRepository.existsByUsername("user4")).thenReturn(false);
        Mockito.when(muncipalRepository.existsByUsername("user4")).thenReturn(false);

        Mockito.when(userRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        Mockito.when(ngoRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        Mockito.when(muncipalRepository.existsByEmail(dto.getEmail())).thenReturn(false);

        Mockito.when(userService.craetenewuser(Mockito.any()))
                .thenReturn("USER CREATED");

        mockMvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("USER CREATED"));
    }

    @Test
    void signup_usernameAlreadyExists() throws Exception {
        UserDTO dto = new UserDTO();
        dto.setUsername("user1");
        dto.setEmail("user@gmail.com");
        dto.setPassword("xxxx");

        Mockito.when(userRepository.existsByUsername("user1")).thenReturn(true);

        mockMvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict())
                .andExpect(content().string("User is username Alraedy Exits"));
    }

    // ---------------- LOGIN TESTS ----------------

    @Test
    void login_success() throws Exception {
        UserloginDto loginDto = new UserloginDto();
        loginDto.setUsername("user1");
        loginDto.setPassword("1234");

        UserDetails userDetails =
                new User("user1", "1234", new ArrayList<>());

        Mockito.when(myUserDetails.loadUserByUsername("user1"))
                .thenReturn(userDetails);

        Mockito.when(jwtUtil.generateToken(userDetails))
                .thenReturn("mock-user-jwt");

        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("mock-user-jwt"));
    }



}
