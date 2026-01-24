package dolpi.Muncipal_Service.ControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import dolpi.Muncipal_Service.Controller.RegisterController;
import dolpi.Muncipal_Service.DTO.RegisterDTO;
import dolpi.Muncipal_Service.Repository.MuncipalCoprationRepo;
import dolpi.Muncipal_Service.Service.RegisterService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RegisterController.class)
public class RegisterControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegisterService registerService;

    @MockBean
    private MuncipalCoprationRepo muncipalCoprationRepo;

    @Autowired
    private ObjectMapper objectMapper;

    // ✅ SUCCESS CASE
    @Test
    void register_success_whenRoleIsMuncipal() throws Exception {

        RegisterDTO dto = new RegisterDTO();
        dto.setMunicipalName("Bareilly Municipal Corporation");
        dto.setState("Uttar Pradesh");
        dto.setCity("Bareilly");
        dto.setOfficeAddress("Golden Fly Colony");
        dto.setEmail("official@bareillymuni.gov.in");
        dto.setHelplineNumber("9870123456");
        dto.setRegistrationCode("UP-BLY-MUNI-001");
        dto.setDescription("Our goal is to rescue injured stray animals");
        // dto ke fields set kar sakte ho

        Mockito.doReturn(false)
                .when(muncipalCoprationRepo)
                .existsByUsername(Mockito.anyString());

        ResponseEntity response= new ResponseEntity("Sucsessfully Register", HttpStatus.CREATED);
        when(registerService.registermuncipal(
                Mockito.anyString(),
                Mockito.any(RegisterDTO.class),
                Mockito.anyString()
        )).thenReturn(response);

        mockMvc.perform(post("/muncipal-register/register")
                        .header("X-USERNAME", "muncipalUser")
                        .header("X-ROLE", "ROLE_MUNCIPAL")
                        .header("X-GATEWAY", "API-GATEWAY")
                        .param("id", "101")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk());
    }

    // FAIL CASE — WRONG ROLE
    @Test
    void register_fail_whenRoleIsInvalid() throws Exception {

        RegisterDTO dto = new RegisterDTO();

        mockMvc.perform(post("/muncipal-register/register")
                        .header("X-USERNAME", "user")
                        .header("X-ROLE", "ROLE_USER")
                        .header("X-GATEWAY", "API-GATEWAY")
                        .param("id", "101")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isNotFound());
    }

}
