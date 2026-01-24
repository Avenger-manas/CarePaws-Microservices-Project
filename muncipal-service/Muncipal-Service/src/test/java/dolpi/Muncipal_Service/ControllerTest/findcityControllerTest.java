package dolpi.Muncipal_Service.ControllerTest;

import dolpi.Muncipal_Service.Controller.FetchReportData;
import dolpi.Muncipal_Service.Controller.findcitycontroller;
import dolpi.Muncipal_Service.DTO.UserNotification;
import dolpi.Muncipal_Service.Entity.RegisterEntity;
import dolpi.Muncipal_Service.Repository.MuncipalCoprationRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(findcitycontroller.class)
public class findcityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MuncipalCoprationRepo muncipalCoprationRepo;

    @Test
    void findbycity() throws Exception {
        List<RegisterEntity>list=new ArrayList<>();
        when(muncipalCoprationRepo.findByCity(
                Mockito.anyString()
                )).thenReturn(list);

        mockMvc.perform(get("/find/getCity")
                .header("X-INTERNAL-TOKEN", "INTERNAl-NGO-AND-MUNICIPAL-SERVICE-TOKEN")
                .header("X-GATEWAY", "API-GATEWAY")
                .param("city", "Bareilly")
        ).andExpect(status().isOk());
    }
}
