package dolpi.Report_Service.Service;

import dolpi.Report_Service.Dto.Notificationmessage;
import dolpi.Report_Service.Dto.ReportDTO;
import dolpi.Report_Service.Entity.ReportEntity;
import dolpi.Report_Service.Repository.ReportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {
    @Mock
    private ReportRepository reportRepository;

    @Mock
    private NotificationProducer notificationProducer;

    @InjectMocks
    private ReportService reportService;

    @Test
    void shouldSaveReportAndSendNotificationSuccessfully() throws IOException {

        // -------- Arrange --------
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setUserid("U1");
        reportDTO.setName("Test User");
        reportDTO.setCity("Delhi");
        reportDTO.setDescription("Test Description");
        reportDTO.setLocation_address("Test Address");
        reportDTO.setMobilenumber("9999999999");
        reportDTO.setPincode("110001");

        MultipartFile file = new MockMultipartFile(
                "file",
                "test.jpg",
                "image/jpeg",
                "dummy image content".getBytes()
        );



        // -------- Act --------
        String response = reportService.report(reportDTO, file);

        // -------- Assert --------
        assertEquals("Succesfully save wait few minutes", response);

        verify(reportRepository, times(1))
                .save(any(ReportEntity.class));

        verify(notificationProducer, times(1))
                .send(any(Notificationmessage.class));
    }
}
