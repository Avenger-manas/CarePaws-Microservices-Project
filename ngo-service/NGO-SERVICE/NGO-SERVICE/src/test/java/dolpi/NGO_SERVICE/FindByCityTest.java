package dolpi.NGO_SERVICE;

import dolpi.NGO_SERVICE.Controller.FindByCity;
import dolpi.NGO_SERVICE.Entity.RegisterNgo;
import dolpi.NGO_SERVICE.Repository.NgoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FindByCity.class)
public class FindByCityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NgoRepository ngoRepository;

    @Test
    void testFindByCity() throws Exception {
        // Mocking repository response
        RegisterNgo ngo = new RegisterNgo();
        ngo.setNgoname("Test NGO");
        ngo.setCity("Bareilly");

        when(ngoRepository.findByCity("Bareilly"))
                .thenReturn(List.of(ngo));

        mockMvc.perform(get("/find/getCity")
                        .param("city", "Bareilly")
                        .header("X-INTERNAL-TOKEN", "INTERNAl-NGO-AND-MUNICIPAL-SERVICE-TOKEN") //internal token
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ngoname").value("Test NGO"))
                .andExpect(jsonPath("$[0].city").value("Bareilly"));
    }
}