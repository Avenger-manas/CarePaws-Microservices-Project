package dolpi.NGO_SERVICE.ServicesTest;

import dolpi.NGO_SERVICE.Dto.Notification;

import dolpi.NGO_SERVICE.Dto.ReportEntity;
import dolpi.NGO_SERVICE.Dto.UserNotification;
import dolpi.NGO_SERVICE.Exception.ResourcesNotFound;
import dolpi.NGO_SERVICE.Interface.NotificationFeign;
import dolpi.NGO_SERVICE.Interface.ReortFeign;
import dolpi.NGO_SERVICE.Service.NgoCallMicroservice;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NgoCallMicroserviceTest {

    @Mock
    private NotificationFeign notificationFeign;

    @Mock
    private ReortFeign reortFeign;

    @InjectMocks
    private NgoCallMicroservice ngoCallMicroservice;

    // ===========================
    // ✅ notificationfeign success
    // ===========================
    @Test
    void notificationfeign_success() {
        Notification notification = new Notification();
        notification.setNgoanmcplId("NGO123");

        when(notificationFeign.checkngomuncipalnotify("NOTIFY1"))
                .thenReturn(Collections.singletonList(notification));

        List<Notification> result = ngoCallMicroservice.notificationfeign("NOTIFY1");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("NGO123", result.get(0).getNgoanmcplId());

        verify(notificationFeign, times(1)).checkngomuncipalnotify("NOTIFY1");
    }

    // ===========================
    //  notificationfeign NOT FOUND
    // ===========================
    @Test
    void notificationfeign_notFound() {
        // Arrange
        Notification n = new Notification();
        n.setNgoanmcplId("NOT FOUND");
        when(notificationFeign.checkngomuncipalnotify(anyString()))
                .thenReturn(List.of(n));

        // Act + Assert
        ResourcesNotFound ex = assertThrows(
                ResourcesNotFound.class,
                () -> ngoCallMicroservice.notificationfeign("someID")
        );

        assertEquals("NOT FOUND", ex.getMessage());
    }

    @Test
    void fetchReport_success() {
        ReportEntity report = new ReportEntity();
        report.setName("Report1");
        UserNotification userNotification = new UserNotification();

        when(reortFeign.fetchreport("RPT1", userNotification))
                .thenReturn(report);

        ReportEntity result = ngoCallMicroservice.fetchReport("RPT1", userNotification);

        assertNotNull(result);
        assertEquals("Report1", result.getName());

        verify(reortFeign, times(1)).fetchreport("RPT1", userNotification);
    }

    // ===========================
    //  fetchReport NOT FOUND
    // ===========================
    @Test
    void fetchReport_notFound() {
        ReportEntity report = new ReportEntity();
        report.setName("NOT FOUND");
        UserNotification userNotification = new UserNotification();

        when(reortFeign.fetchreport("RPT1", userNotification))
                .thenReturn(report);

        ResourcesNotFound ex = assertThrows(
                ResourcesNotFound.class,
                () -> ngoCallMicroservice.fetchReport("RPT1", userNotification)
        );

        assertEquals("NOT FOUND", ex.getMessage());
        verify(reortFeign, times(1)).fetchreport("RPT1", userNotification);
    }


    // ===========================
    // notificationfeign fallback
    // ===========================
    @Test
    void notificationfeign_fallback() {

        List<Notification> result = ngoCallMicroservice.ngoFallback("BADID", new RuntimeException());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Server Side Problem", result.get(0).getNgoanmcplId());
    }


    // ===========================
    // fetchReport fallback
    // ===========================
    @Test
    void fetchReport_fallback() {
        UserNotification userNotification = new UserNotification();
        ReportEntity result = ngoCallMicroservice.ReportFallBack("BADID", userNotification, new RuntimeException());

        assertNotNull(result);
        assertEquals("Server Side Problem", result.getName());
    }


}
