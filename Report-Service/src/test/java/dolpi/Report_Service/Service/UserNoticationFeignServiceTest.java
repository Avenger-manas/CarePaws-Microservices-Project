package dolpi.Report_Service.Service;

import dolpi.Report_Service.Dto.UserNotification;
import dolpi.Report_Service.Interface.NotificationFeign;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserNoticationFeignServiceTest {

    @Mock
    private NotificationFeign notificationFeign;

    @InjectMocks
    private UserNoticationFeignService userNoticationFeignService;

    //  Success Case
    @Test
    void shouldSendUserNotificationSuccessfully() {

        when(notificationFeign.saveusernotification(any(UserNotification.class)))
                .thenReturn("Notification Sent");

        UserNotification userNotification = new UserNotification();

        String response =
                userNoticationFeignService.UserNotify(userNotification);

        assertEquals("Notification Sent", response);
    }

    // Fallback Method Test
    @Test
    void shouldReturnFallbackResponseWhenServiceDown() {

        UserNotification userNotification = new UserNotification();

        String response =
                userNoticationFeignService
                        .UserNotification(userNotification, new RuntimeException("Service down"));

        assertEquals("Server is Down", response);
    }

}
