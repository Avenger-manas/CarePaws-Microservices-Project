package dolpi.NGO_SERVICE;

import com.fasterxml.jackson.databind.ObjectMapper;
import dolpi.NGO_SERVICE.Controller.FetchReportData;
import dolpi.NGO_SERVICE.Dto.ReportEntity;
import dolpi.NGO_SERVICE.Dto.UserNotification;
import dolpi.NGO_SERVICE.Service.FetchReportDataService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FetchReportData.class)
class FetchReportDataTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FetchReportDataService fetchReportDataService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnReportWhenRoleIsNGO() throws Exception {

        UserNotification userNotification = new UserNotification();
        userNotification.setNGOanMNCPL_Name("test message");
        userNotification.setTIME_INFOan_DSC("2026-01-12T10:00:00");

        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setName("SUCCESS");

        when(fetchReportDataService.reportdata(
                anyString(), anyString(), any(UserNotification.class)
        )).thenReturn(reportEntity);

        mockMvc.perform(
                        post("/ngo/getReport")
                                .header("X-USERNAME", "testUser")
                                .header("X-ROLE", "ROLE_NGO")
                                .header("X-GATEWAY", "API")
                                .param("id", "123")
                                .param("ngoId", "456")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userNotification))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("SUCCESS"));
    }
}