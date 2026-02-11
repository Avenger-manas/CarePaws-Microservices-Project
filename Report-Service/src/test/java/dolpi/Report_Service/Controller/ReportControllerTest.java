package dolpi.Report_Service.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dolpi.Report_Service.Dto.ReportDTO;
import dolpi.Report_Service.Service.ReportService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReportController.class)
public class ReportControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService reportService;

    @Autowired
    private ObjectMapper objectMapper;

    // CASE 1: ROLE_USER → SUCCESS
    @Test
    void reportComplaint_success() throws Exception {

        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setMobilenumber("dkd");
        reportDTO.setName("jsj");
        reportDTO.setPincode("jj");
        reportDTO.setCity("kaja");
        reportDTO.setLocation_address("ksks");
        reportDTO.setUserid("123");
        reportDTO.setDescription("Animal cruelty case");

        MockMultipartFile file =
                new MockMultipartFile(
                        "File",
                        "image.jpg",
                        MediaType.IMAGE_JPEG_VALUE,
                        "dummy-image".getBytes()
                );

        Mockito.when(reportService.report(
                Mockito.any(ReportDTO.class),
                Mockito.any()
        )).thenReturn("Report Submitted");




        mockMvc.perform(
                        multipart("/report/complaint")
                                .file(file)
                                .param("report", objectMapper.writeValueAsString(reportDTO))
                                .header("X-USERNAME", "dolpi")
                                .header("X-ROLE", "ROLE_USER")
                                .header("X-GATEWAY", "API-GATEWAY")
                )
                .andExpect(status().isOk());
    }

    //  CASE 2: ROLE ≠ ROLE_USER → NOT FOUND (Exception)
    @Test
    void reportComplaint_invalidRole() throws Exception {

        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setMobilenumber("dkd");
        reportDTO.setName("jsj");
        reportDTO.setPincode("jj");
        reportDTO.setCity("kaja");
        reportDTO.setLocation_address("ksks");
        reportDTO.setUserid("123");
        reportDTO.setDescription("Animal cruelty case");

        MockMultipartFile file =
                new MockMultipartFile(
                        "File",
                        "image.jpg",
                        MediaType.IMAGE_JPEG_VALUE,
                        "dummy-image".getBytes()
                );

        mockMvc.perform(
                        multipart("/report/complaint")
                                .file(file)
                                .param("report", objectMapper.writeValueAsString(reportDTO))
                                .header("X-USERNAME", "dolpi")
                                .header("X-ROLE", "ROLE_ADMIN")
                                .header("X-GATEWAY", "API-GATEWAY")
                )
                .andExpect(status().isNotFound());
    }

}
