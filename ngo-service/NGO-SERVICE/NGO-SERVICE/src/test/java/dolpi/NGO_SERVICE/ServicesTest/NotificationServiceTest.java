package dolpi.NGO_SERVICE.ServicesTest;
import dolpi.NGO_SERVICE.Dto.Notification;
import dolpi.NGO_SERVICE.Exception.ResourcesNotFound;
import dolpi.NGO_SERVICE.Service.ResucueNotifyService;
import dolpi.NGO_SERVICE.Service.NgoCallMicroservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {
    @Mock
    private NgoCallMicroservice ngoCallMicroservice;

    @InjectMocks
    private ResucueNotifyService resucueNotifyService;


    // ===========================
    // ✅ SUCCESS CASE
    // ===========================
    @Test
    void notifyservice_success() {
        // Arrange
        Notification notification = new Notification();
        notification.setNgoanmcplId("NGO123");

        when(ngoCallMicroservice.notificationfeign("SUB123"))
                .thenReturn(Collections.singletonList(notification));

        // Act
        List<Notification> result = resucueNotifyService.notifyservice("SUB123");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("NGO123", result.get(0).getNgoanmcplId());

        verify(ngoCallMicroservice, times(1)).notificationfeign("SUB123");
    }

    // ===========================
    // ❌ NOT FOUND CASE
    // ===========================
    @Test
    void notifyservice_notFound() {
        // Arrange
        Notification notification = new Notification();
        notification.setNgoanmcplId("NOT FOUND");

        when(ngoCallMicroservice.notificationfeign("SUB123"))
                .thenReturn(Collections.singletonList(notification));

        // Act + Assert
        ResourcesNotFound ex = assertThrows(
                ResourcesNotFound.class,
                () -> resucueNotifyService.notifyservice("SUB123")
        );

        assertEquals("NOT FOUND", ex.getMessage());

        verify(ngoCallMicroservice, times(1)).notificationfeign("SUB123");
    }

    // ===========================
    // ❌ SERVER SIDE PROBLEM CASE
    // ===========================
    @Test
    void notifyservice_serverSideProblem() {
        // Arrange
        Notification notification = new Notification();
        notification.setNgoanmcplId("Server Side Problem");

        when(ngoCallMicroservice.notificationfeign("SUB123"))
                .thenReturn(Collections.singletonList(notification));

        // Act + Assert
        ResourcesNotFound ex = assertThrows(
                ResourcesNotFound.class,
                () -> resucueNotifyService.notifyservice("SUB123")
        );

        assertEquals("Server Side Problem", ex.getMessage());

        verify(ngoCallMicroservice, times(1)).notificationfeign("SUB123");
    }


}
