package dolpi.Muncipal_Service.ServiceTest;


import dolpi.Muncipal_Service.DTO.Notification;
import dolpi.Muncipal_Service.Exception.ResourcesNotFound;
import dolpi.Muncipal_Service.Service.MuncipalCallMicroservice;
import dolpi.Muncipal_Service.Service.ResucueNotifyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ResucueNotifyServiceTest {
    @Mock
    private MuncipalCallMicroservice muncipalCallMicroservice;

    @InjectMocks
    private ResucueNotifyService resucueNotifyService;

    // ===================== SUCCESS CASE =====================

    @Test
    void notifyservice_success() {

        String submissionId = "123";

        Notification notification = new Notification();
        notification.setNgoanmcplId("NGO123");

        List<Notification> list = new ArrayList<>();
        list.add(notification);

        when(muncipalCallMicroservice.notificationfeign(submissionId))
                .thenReturn(list);

        List<Notification> result =
                resucueNotifyService.notifyservice(submissionId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("NGO123", result.get(0).getNgoanmcplId());

        verify(muncipalCallMicroservice, times(1))
                .notificationfeign(submissionId);
    }

    // ===================== NOT FOUND CASE =====================

    @Test
    void notifyservice_notFound_exception() {

        String submissionId = "123";

        Notification notification = new Notification();
        notification.setNgoanmcplId("NOT FOUND");

        List<Notification> list = new ArrayList<>();
        list.add(notification);

        when(muncipalCallMicroservice.notificationfeign(submissionId))
                .thenReturn(list);

        assertThrows(ResourcesNotFound.class, () ->
                resucueNotifyService.notifyservice(submissionId));
    }

    // ===================== SERVER SIDE PROBLEM CASE =====================

    @Test
    void notifyservice_serverSideProblem_exception() {

        String submissionId = "123";

        Notification notification = new Notification();
        notification.setNgoanmcplId("Server Side Problem");

        List<Notification> list = new ArrayList<>();
        list.add(notification);

        when(muncipalCallMicroservice.notificationfeign(submissionId))
                .thenReturn(list);

        assertThrows(ResourcesNotFound.class, () ->
                resucueNotifyService.notifyservice(submissionId));
    }

}
