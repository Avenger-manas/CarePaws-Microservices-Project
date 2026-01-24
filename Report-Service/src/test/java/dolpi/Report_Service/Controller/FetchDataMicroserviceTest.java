package dolpi.Report_Service.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dolpi.Report_Service.Dto.UserNotification;
import dolpi.Report_Service.Entity.ReportEntity;
import dolpi.Report_Service.Repository.ReportRepository;
import dolpi.Report_Service.Service.ReportNotificationService;
import dolpi.Report_Service.Service.UserNotificationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FetchDataMicroservice.class)
public class FetchDataMicroserviceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportRepository reportRepository;

    @MockBean
    private UserNotificationService userNotificationService;

    @MockBean
    private ReportNotificationService reportNotificationService;

    @Autowired
    private ObjectMapper objectMapper;

    // CASE 1: Report FOUND
    @Test
    void fetchReport_success() throws Exception {

        String reportId = "123";

        ReportEntity report = new ReportEntity();
        report.setName("Test Report");
        report.setDescription("Test Description");

        UserNotification userNotification = new UserNotification();
        userNotification.setTIME_INFOan_DSC("Hello");
        userNotification.setNGOanMNCPL_Name("user");

        Mockito.when(reportRepository.findById(reportId))
                .thenReturn(Optional.of(report));

        mockMvc.perform(post("/fetch/GetReport")
                        .param("id", reportId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userNotification)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Report"))
                .andExpect(jsonPath("$.description").value("Test Description"));

        Mockito.verify(userNotificationService)
                .Notificationuser(Mockito.any(UserNotification.class));

        Mockito.verify(reportNotificationService)
                .deletenotificationdata(reportId);
    }

    // CASE 2: Report NOT FOUND
    @Test
    void fetchReport_notFound() throws Exception {

        String reportId = "999";

        UserNotification userNotification = new UserNotification();
        userNotification.setTIME_INFOan_DSC("Hello");
        userNotification.setNGOanMNCPL_Name("user");

        Mockito.when(reportRepository.findById(reportId))
                .thenReturn(Optional.empty());

        mockMvc.perform(post("/fetch/GetReport")
                        .param("id", reportId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userNotification)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("NOT FOUND"))
                .andExpect(jsonPath("$.description").value("NOT NULL"));

        Mockito.verify(userNotificationService, Mockito.never())
                .Notificationuser(Mockito.any());

        Mockito.verify(reportNotificationService, Mockito.never())
                .deletenotificationdata(Mockito.anyString());
    }
}
