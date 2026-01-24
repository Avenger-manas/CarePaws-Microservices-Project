package dolpi.Report_Service.Service;

import dolpi.Report_Service.Dto.UserNotification;
import dolpi.Report_Service.Exception.ResourcesNotFound;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserNotificationServiceTest {
    @Mock
    private UserNoticationFeignService userNoticationFeignService;

    @InjectMocks
    private UserNotificationService userNotificationService;

    // Success case
    @Test
    void shouldReturnResponseWhenServerIsUp() {

        UserNotification userNotification = new UserNotification();

        when(userNoticationFeignService.UserNotify(any(UserNotification.class)))
                .thenReturn("Notification Sent");

        String response =
                userNotificationService.Notificationuser(userNotification);

        assertEquals("Notification Sent", response);
    }

    // Failure case
    @Test
    void shouldThrowExceptionWhenServerIsDown() {

        UserNotification userNotification = new UserNotification();

        when(userNoticationFeignService.UserNotify(any(UserNotification.class)))
                .thenReturn("Server is Down");

        assertThrows(ResourcesNotFound.class, () ->
                userNotificationService.Notificationuser(userNotification)
        );
    }
}
