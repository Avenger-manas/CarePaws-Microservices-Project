package dolpi.Report_Service.Service;

import dolpi.Report_Service.Dto.Notifiaction;
import dolpi.Report_Service.Interface.NotificationFeign;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class NotificationFeignServiceTest {
    @Mock
    private NotificationFeign notificationFeign;

    @InjectMocks
    private NotificationFeignService notificationFeignService;

    //  Success Case
    @Test
    void shouldSaveNotificationSuccessfully() {

        Notifiaction notifiaction = new Notifiaction();
        notifiaction.setSubmissionId("123");

        doNothing().when(notificationFeign)
                .savenotification(any(Notifiaction.class));

        assertDoesNotThrow(() ->
                notificationFeignService.savenotifiaction(notifiaction)
        );

        verify(notificationFeign, times(1))
                .savenotification(notifiaction);
    }

    //  Failure Case → fallback
    @Test
    void shouldCallFallbackWhenFeignFails() {

        Notifiaction notifiaction = new Notifiaction();
        notifiaction.setSubmissionId("456");

        RuntimeException exception =
                new RuntimeException("Feign service down");

        // direct fallback test
        assertDoesNotThrow(() ->
                notificationFeignService
                        .notificationFallback(notifiaction, exception)
        );
    }
}
