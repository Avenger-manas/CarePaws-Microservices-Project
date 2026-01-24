package dolpi.CarePaws.ControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import dolpi.CarePaws.Controller.MuncipalCoprationController;
import dolpi.CarePaws.DTO.MuncipalDTO;
import dolpi.CarePaws.DTO.muncipallogin;
import dolpi.CarePaws.JwtToken.JwtUtil;
import dolpi.CarePaws.Repository.MuncipalRepository;
import dolpi.CarePaws.Repository.NgoRepository;
import dolpi.CarePaws.Repository.UserRepository;
import dolpi.CarePaws.Service.MuncipalService;
import dolpi.CarePaws.Service.MyUserDetails;
import dolpi.CarePaws.Service.NgoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MuncipalCoprationController.class)
@AutoConfigureMockMvc(addFilters = false)
class MuncipalCoprationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MuncipalService muncipalService;

    @MockBean
    private MuncipalRepository muncipalRepository;

    @MockBean
    private NgoRepository ngoRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private MyUserDetails myUserDetails;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private NgoService ngoService;

    @Test
    void signupMuncipal_success() throws Exception {

        MuncipalDTO dto = new MuncipalDTO();
        dto.setUsername("muncipal1");
        dto.setPassword("random");
        dto.setEmail("test@gmail.com");

        when(muncipalRepository.existsByUsername("muncipal1")).thenReturn(false);
        when(userRepository.existsByUsername("muncipal1")).thenReturn(false);
        when(ngoRepository.existsByUsername("muncipal1")).thenReturn(false);

        when(muncipalRepository.existsByEmail("test@gmail.com")).thenReturn(false);
        when(userRepository.existsByEmail("test@gmail.com")).thenReturn(false);
        when(ngoRepository.existsByEmail("test@gmail.com")).thenReturn(false);

        when(muncipalService.craetenewmuncipal(any()))
                .thenReturn("Muncipal Created");

        mockMvc.perform(post("/muncipal/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Muncipal Created"));
    }

    @Test
    void signupMuncipal_usernameExists() throws Exception {

        when(muncipalRepository.existsByUsername("muncipal1"))
                .thenReturn(true);

        MuncipalDTO dto = new MuncipalDTO();
        dto.setUsername("muncipal1");
        dto.setPassword("random");
        dto.setEmail("test@gmail.com");

        mockMvc.perform(post("/muncipal/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isConflict());
    }

    @Test
    void loginMuncipal_success() throws Exception {

        muncipallogin login = new muncipallogin();
        login.setUsername("muncipal1");
        login.setPassword("password");

        UserDetails userDetails =
                new org.springframework.security.core.userdetails.User(
                        "muncipal1", "password", new ArrayList<>());

        when(myUserDetails.loadUserByUsername("muncipal1"))
                .thenReturn(userDetails);

        when(jwtUtil.generateToken(userDetails))
                .thenReturn("jwt-token");

        mockMvc.perform(post("/muncipal/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(login)))
                .andExpect(status().isCreated())
                .andExpect(content().string("jwt-token"));
    }



}
