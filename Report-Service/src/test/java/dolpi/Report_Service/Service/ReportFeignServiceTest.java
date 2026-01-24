package dolpi.Report_Service.Service;

import dolpi.Report_Service.Interface.NotificationFeign;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportFeignServiceTest {
    @Mock
    private NotificationFeign notificationFeign;

    @InjectMocks
    private ReportFeignService reportFeignService;

    // Success Case
    @Test
    void shouldDeleteNotificationSuccessfully() {

        when(notificationFeign.deletenotification(anyString()))
                .thenReturn("Deleted Successfully");

        String response =
                reportFeignService.deletenotification("123");

        assertEquals("Deleted Successfully", response);
    }

    // Fallback Method Test
    @Test
    void shouldReturnFallbackMessageWhenServiceDown() {

        String response =
                reportFeignService.DeleteNotification("123");

        assertEquals("Server is Down", response);
    }
}
