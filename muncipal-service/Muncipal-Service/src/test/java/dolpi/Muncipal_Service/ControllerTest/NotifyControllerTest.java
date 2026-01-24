package dolpi.Muncipal_Service.ControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import dolpi.Muncipal_Service.Controller.NotifyController;
import dolpi.Muncipal_Service.DTO.Notification;
import dolpi.Muncipal_Service.Service.ResucueNotifyService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(NotifyController.class)
public class NotifyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResucueNotifyService resucueNotifyService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getNotify() throws Exception {
        List<Notification>list=new ArrayList<>();
        when(resucueNotifyService.notifyservice(Mockito.anyString())).thenReturn(list);

        mockMvc.perform(get("/muncipal-register/notify")
                        .header("X-USERNAME", "muncipalUser")
                        .header("X-ROLE", "ROLE_MUNCIPAL")
                        .header("X-GATEWAY", "API-GATEWAY")
                        .param("ngomnplId", "202")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    //this is test exception
    @Test
    void getNotifyException() throws Exception {
        List<Notification>list=new ArrayList<>();
        when(resucueNotifyService.notifyservice(Mockito.anyString())).thenReturn(list);

        mockMvc.perform(get("/muncipal-register/notify")
                        .header("X-USERNAME", "muncipalUser")
                        .header("X-ROLE", "ROLE_USER")
                        .header("X-GATEWAY", "API-GATEWAY")
                        .param("ngomnplId", "202")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }
}
