package dolpi.Muncipal_Service.ControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import dolpi.Muncipal_Service.Controller.FetchReportData;
import dolpi.Muncipal_Service.DTO.ReportEntity;
import dolpi.Muncipal_Service.DTO.UserNotification;
import dolpi.Muncipal_Service.Service.FetchReportDataService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FetchReportData.class)
public class FetchReportDataTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FetchReportDataService fetchReportDataService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getReport_success_whenRoleIsMuncipal() throws Exception{

        UserNotification notification = new UserNotification();
        notification.setNGOanMNCPL_Name("123");
        notification.setTIME_INFOan_DSC("213");
        // notification fields set kar sakte ho

        ReportEntity report = new ReportEntity();
        // report fields set kar sakte ho

        when(fetchReportDataService.GetReportData(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.any(UserNotification.class)
        )).thenReturn(report);

        mockMvc.perform(post("/muncipal-register/getReport")
                        .header("X-USERNAME", "muncipalUser")
                        .header("X-ROLE", "ROLE_MUNCIPAL")
                        .header("X-GATEWAY", "API-GATEWAY")
                        .param("id", "101")
                        .param("ngomuncipalId", "202")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(notification))
                )
                .andExpect(status().isOk());
    }

    // FAILURE CASE
    @Test
    void getReport_fail_whenRoleIsInvalid() throws Exception {

        UserNotification notification = new UserNotification();
        notification.setNGOanMNCPL_Name("123");
        notification.setTIME_INFOan_DSC("213");

        mockMvc.perform(post("/muncipal-register/getReport")
                        .header("X-USERNAME", "user")
                        .header("X-ROLE", "ROLE_USER")
                        .header("X-GATEWAY", "API-GATEWAY")
                        .param("id", "101")
                        .param("ngomuncipalId", "202")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(notification))
                )
                .andExpect(status().isNotFound());
    }

}
