package dolpi.Report_Service.Service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportNotificationServiceTest {
    @Mock
    private ReportFeignService reportFeignService;

    @InjectMocks
    private ReportNotificationService reportNotificationService;

    // Success case
    @Test
    void shouldNotThrowExceptionWhenServerIsUp() {

        when(reportFeignService.deletenotification(anyString()))
                .thenReturn("Deleted Successfully");

        assertDoesNotThrow(() ->
                reportNotificationService.deletenotificationdata("123")
        );
    }
}
