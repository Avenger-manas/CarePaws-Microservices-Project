package dolpi.Muncipal_Service.ServiceTest;

import dolpi.Muncipal_Service.DTO.Notification;
import dolpi.Muncipal_Service.DTO.ReportEntity;
import dolpi.Muncipal_Service.DTO.UserNotification;
import dolpi.Muncipal_Service.Exception.ResourcesNotFound;
import dolpi.Muncipal_Service.Interfcae.NotificationFeign;
import dolpi.Muncipal_Service.Interfcae.ReportFeign;
import dolpi.Muncipal_Service.Service.MuncipalCallMicroservice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;

import static org.bson.assertions.Assertions.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


@ExtendWith(MockitoExtension.class)
public class MuncipalCallMicroserviceTest {
    @Mock
    private ReportFeign reportFeign;

    @Mock
    private NotificationFeign notificationFeign;

    @InjectMocks
    private MuncipalCallMicroservice muncipalCallMicroservice;

    //This Is Notifiction
    @Test
    void notificationfeign_success() {

        Notification notification=new Notification();
        notification.setNgoanmcplId("123");
        List<Notification> list = new ArrayList<>();
        list.add(notification);

        when(notificationFeign.checkngomuncipalnotify("123"))
                .thenReturn(list);

        List<Notification> result =
                muncipalCallMicroservice.notificationfeign("123");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("123", result.get(0).getNgoanmcplId());

        verify(notificationFeign, times(1))
                .checkngomuncipalnotify("123");


    }

    //Not Found

    @Test
    void notificationfeign_notFound_exception() {
        String notifyId = "123";

        Notification n=new Notification();
        n.setNgoanmcplId("NOT FOUND");
        List<Notification> list = new ArrayList<>();
        list.add(n); // simulate NOT FOUND

        when(notificationFeign.checkngomuncipalnotify(notifyId))
                .thenReturn(list);

        assertThrows(ResourcesNotFound.class, () ->
                muncipalCallMicroservice.notificationfeign(notifyId));
    }

    //Fall Back Method
    @Test
    void Notification_FallBack(){
        List<Notification> result = muncipalCallMicroservice.muncipalFallback("123", new RuntimeException());
        assertNotNull(result);
        assertEquals("Server Side Problem", result.get(0).getNgoanmcplId());
    }


// ===================== fetchReport() =====================

    @Test
    void fetchReport_success() {
        String id = "123";
        UserNotification userNotification = new UserNotification();

        ReportEntity report = new ReportEntity();
        report.setName("Valid Report");

        when(reportFeign.fetchreport(id, userNotification))
                .thenReturn(report);

        ReportEntity result =
                muncipalCallMicroservice.fetchReport(id, userNotification);

        assertNotNull(result);
        assertEquals("Valid Report", result.getName());

        verify(reportFeign, times(1))
                .fetchreport(id, userNotification);
    }

    //Not Found Problem
    @Test
    void fetchReport_notFound_exception() {
        String id = "1";
        UserNotification userNotification = new UserNotification();

        ReportEntity report = new ReportEntity();
        report.setName("NOT FOUND");

        when(reportFeign.fetchreport(id, userNotification))
                .thenReturn(report);

        assertThrows(ResourcesNotFound.class, () ->
                muncipalCallMicroservice.fetchReport(id, userNotification));
    }


    //Fall Back Method
    @Test
    void FetchReport_FallBack(){
        UserNotification userNotification = new UserNotification();

        ReportEntity result =muncipalCallMicroservice.ReportFallBack("123",userNotification, new RuntimeException());
        assertNotNull(result);
        assertEquals("Server Side Problem", result.getName());
    }






}
