package dolpi.NGO_SERVICE.ServicesTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import dolpi.NGO_SERVICE.Dto.ReportEntity;
import dolpi.NGO_SERVICE.Dto.UserNotification;
import dolpi.NGO_SERVICE.Entity.*;
import dolpi.NGO_SERVICE.Exception.ResourcesNotFound;
import dolpi.NGO_SERVICE.Repository.NgoNotificationRepo;
import dolpi.NGO_SERVICE.Service.FetchReportDataService;
import dolpi.NGO_SERVICE.Service.NgoCallMicroservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FetchReportDataServiceTest {

    @Mock
    private NgoCallMicroservice ngoCallMicroservice;

    @Mock
    private NgoNotificationRepo notificationRepo;

    @InjectMocks
    private FetchReportDataService fetchReportDataService;

    // ===========================
    // ✅ SUCCESS CASE
    // ===========================
    @Test
    void reportdata_success() {

        // Arrange
        ReportEntity report = new ReportEntity();
        report.setName("CarePaws Report");

        when(ngoCallMicroservice.fetchReport(anyString(), any(UserNotification.class)))
                .thenReturn(report);

        // Act
        ReportEntity result = fetchReportDataService.reportdata(
                "123", "ngo123", new UserNotification()
        );

        // Assert
        assertNotNull(result);
        assertEquals("CarePaws Report", result.getName());

        verify(notificationRepo, times(1)).save(any(NgoNotification.class));
    }

    // ===========================
    // ❌ REPORT NOT FOUND
    // ===========================
    @Test
    void reportdata_notFound() {

        // Arrange
        ReportEntity report = new ReportEntity();
        report.setName("NOT FOUND");

        when(ngoCallMicroservice.fetchReport(anyString(), any(UserNotification.class)))
                .thenReturn(report);

        // Act + Assert
        ResourcesNotFound ex = assertThrows(
                ResourcesNotFound.class,
                () -> fetchReportDataService.reportdata(
                        "123", "ngo123", new UserNotification()
                )
        );

        assertEquals("NOT FOUND", ex.getMessage());

        verify(notificationRepo, never()).save(any());
    }

    // ===========================
    // ❌ SERVER SIDE PROBLEM
    // ===========================
    @Test
    void reportdata_serverSideProblem() {

        // Arrange
        ReportEntity report = new ReportEntity();
        report.setName("Server Side Problem");

        when(ngoCallMicroservice.fetchReport(anyString(), any(UserNotification.class)))
                .thenReturn(report);

        // Act + Assert
        ResourcesNotFound ex = assertThrows(
                ResourcesNotFound.class,
                () -> fetchReportDataService.reportdata(
                        "123", "ngo123", new UserNotification()
                )
        );

        assertEquals("Server Side Problem", ex.getMessage());

        verify(notificationRepo, never()).save(any());
    }
}