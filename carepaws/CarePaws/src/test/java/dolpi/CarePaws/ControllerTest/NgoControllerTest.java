package dolpi.CarePaws.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dolpi.CarePaws.DTO.NgoDto;
import dolpi.CarePaws.DTO.NgoLoginDto;
import dolpi.CarePaws.JwtToken.JwtUtil;
import dolpi.CarePaws.Repository.MuncipalRepository;
import dolpi.CarePaws.Repository.NgoRepository;
import dolpi.CarePaws.Repository.UserRepository;
import dolpi.CarePaws.Service.MyUserDetails;
import dolpi.CarePaws.Service.NgoService;
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

@WebMvcTest(NgoController.class)
@AutoConfigureMockMvc(addFilters = false)
public class NgoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NgoService ngoService;

    @MockBean
    private NgoRepository ngoRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private MuncipalRepository muncipalRepository;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private MyUserDetails myUserDetails;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    //succes test
    @Test
    void signup_success() throws Exception {
        NgoDto dto = new NgoDto();
        dto.setUsername("ngo3");
        dto.setEmail("ngo3@gmail.com");
        dto.setPassword("xxxxx");

        Mockito.when(ngoRepository.existsByUsername("ngo3")).thenReturn(false);
        Mockito.when(userRepository.existsByUsername("ngo3")).thenReturn(false);
        Mockito.when(muncipalRepository.existsByUsername("ngo3")).thenReturn(false);

        Mockito.when(ngoRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        Mockito.when(userRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        Mockito.when(muncipalRepository.existsByEmail(dto.getEmail())).thenReturn(false);

        Mockito.when(ngoService.craetenewngo(Mockito.any())).thenReturn("NGO CREATED");

        mockMvc.perform(post("/ngo/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("NGO CREATED"));
    }

    //already exits
    @Test
    void signup_usernameAlreadyExistsInNgo() throws Exception {
        NgoDto dto = new NgoDto();
        dto.setUsername("ngo1");
        dto.setEmail("ngo@gmail.com");
        dto.setPassword("XXX");

        Mockito.when(ngoRepository.existsByUsername("ngo1")).thenReturn(true);

        mockMvc.perform(post("/ngo/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Ngo username is  Alraedy Exits"));
    }

    @Test
    void login_success() throws Exception {
        NgoLoginDto loginDto = new NgoLoginDto();
        loginDto.setUsername("ngo1");
        loginDto.setPassword("1234");

        UserDetails userDetails =
                new User("ngo1", "1234", new ArrayList<>());

        Mockito.when(myUserDetails.loadUserByUsername("ngo1"))
                .thenReturn(userDetails);

        Mockito.when(jwtUtil.generateToken(userDetails))
                .thenReturn("mock-jwt-token");

        mockMvc.perform(post("/ngo/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("mock-jwt-token"));
    }

}
