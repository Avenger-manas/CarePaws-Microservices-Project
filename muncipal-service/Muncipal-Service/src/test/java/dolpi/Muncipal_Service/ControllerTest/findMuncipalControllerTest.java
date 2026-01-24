package dolpi.Muncipal_Service.ControllerTest;

import dolpi.Muncipal_Service.Controller.findMuncipalController;
import dolpi.Muncipal_Service.Entity.RegisterEntity;
import dolpi.Muncipal_Service.Repository.MuncipalCoprationRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(findMuncipalController.class)
public class findMuncipalControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MuncipalCoprationRepo muncipalCoprationRepo;

    @Test
    void getcitybasemuncipal_success() throws Exception {

        List<RegisterEntity> list = new ArrayList<>();

        Mockito.when(muncipalCoprationRepo.findByCity(
                Mockito.anyString()
        )).thenReturn(list);

        mockMvc.perform(get("/find/getcity")
                        .header("X-INTERNAL-TOKEN", "INTERNAl-NGO-AND-MUNICIPAL-SERVICE-TOKEN")
                        .param("city", "Bareilly")
                )
                .andExpect(status().isOk());
    }
}
