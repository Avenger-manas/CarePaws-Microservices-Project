package dolpi.NGO_SERVICE;

import com.fasterxml.jackson.databind.ObjectMapper;
import dolpi.NGO_SERVICE.Controller.NgoRegister;
import dolpi.NGO_SERVICE.Dto.RegisterNgodto;
import dolpi.NGO_SERVICE.Repository.NgoRepository;
import dolpi.NGO_SERVICE.Service.NgoService;
import dolpi.NGO_SERVICE.Service.ResucueNotifyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NgoRegister.class)
public class NgoRegisterTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NgoRepository ngoRepository;

    @MockBean
    private NgoService ngoService;

    @MockBean
    private ResucueNotifyService resucueNotifyService;

    // ------------------ SUCCESS CASE ------------------
    @Test
    void testRegisterNgoSuccess() throws Exception {
        RegisterNgodto dto = new RegisterNgodto();
        dto.setNgoname("CarePaws");
        dto.setCity("Bareilly");
        dto.setEmail("carepaws@test.com");
        dto.setAddress("123 Street");
        dto.setPhonenumber("9876543210");
        dto.setAnimaltype("Dog");
        dto.setDescription("Helping stray animals");

        when(ngoRepository.existsByUsername("testUser")).thenReturn(false);
        when(ngoService.registerngo(anyString(), any(RegisterNgodto.class), anyString()))
                .thenReturn("Ngo Sucessfully Register");

        mockMvc.perform(post("/ngo/register")
                        .param("id", "123")
                        .header("X-USERNAME", "testUser")
                        .header("X-ROLE", "ROLE_NGO")
                        .header("X-GATEWAY", "API")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated()) // Controller should return CREATED
                .andExpect(jsonPath("$").value("Ngo Sucessfully Register"));
    }
    // ------------------ NGO ALREADY EXISTS ------------------
    @Test
    void testRegisterNgoAlreadyExists() throws Exception {
        RegisterNgodto dto = new RegisterNgodto();
        dto.setNgoname("CarePaws");
        dto.setCity("Bareilly");
        dto.setDescription("I am Super");
        dto.setAddress("ram nager");
        dto.setPhonenumber("9870913954");
        dto.setAnimaltype("dog cat");
        dto.setEmail("manasrastogi");
        when(ngoRepository.existsByUsername("testUser")).thenReturn(true);

        mockMvc.perform(post("/ngo/register")
                        .param("id", "123")
                        .header("X-USERNAME", "testUser")
                        .header("X-ROLE", "ROLE_NGO")
                        .header("X-GATEWAY", "API")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    // ------------------ ROLE NOT ALLOWED ------------------
    @Test
    void testRegisterNgoRoleNotAllowed() throws Exception {
        RegisterNgodto dto = new RegisterNgodto();
        dto.setNgoname("CarePaws");
        dto.setCity("Bareilly");
        dto.setDescription("I am Super");
        dto.setAddress("ram nager");
        dto.setPhonenumber("9870913954");
        dto.setAnimaltype("dog cat");
        dto.setEmail("manasrastogi");

        mockMvc.perform(post("/ngo/register")
                        .param("id", "123")
                        .header("X-USERNAME", "testUser")
                        .header("X-ROLE", "ROLE_USER") // wrong role
                        .header("X-GATEWAY", "API")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }
}