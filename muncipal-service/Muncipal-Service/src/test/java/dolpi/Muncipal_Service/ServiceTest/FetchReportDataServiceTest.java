package dolpi.Muncipal_Service.ServiceTest;

import dolpi.Muncipal_Service.DTO.ReportEntity;
import dolpi.Muncipal_Service.DTO.UserNotification;
import dolpi.Muncipal_Service.Entity.MuncipalNotification;
import dolpi.Muncipal_Service.Exception.ResourcesNotFound;
import dolpi.Muncipal_Service.Repository.MuncipalRepo;
import dolpi.Muncipal_Service.Service.FetchReportDataService;
import dolpi.Muncipal_Service.Service.MuncipalCallMicroservice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class FetchReportDataServiceTest {
    @Mock
    private MuncipalRepo muncipalRepo;

    @Mock
    private MuncipalCallMicroservice muncipalCallMicroservice;

    @InjectMocks
    private FetchReportDataService fetchReportDataService;

    //  SUCCESS CASE
    @Test
    void getReportData_success() {

        UserNotification userNotification = new UserNotification();

        ReportEntity report = new ReportEntity();
        report.setName("SUCCESS");

        Mockito.when(muncipalCallMicroservice.fetchReport(
                Mockito.anyString(),
                Mockito.any(UserNotification.class)
        )).thenReturn(report);

        ReportEntity result = fetchReportDataService
                .GetReportData("101", "NGO-1", userNotification);

        assertEquals("SUCCESS", result.getName());

        Mockito.verify(muncipalRepo)
                .save(Mockito.any(MuncipalNotification.class));
    }

    //  NOT FOUND CASE
    @Test
    void getReportData_notFound() {

        UserNotification userNotification = new UserNotification();

        ReportEntity report = new ReportEntity();
        report.setName("NOT FOUND");

        Mockito.when(muncipalCallMicroservice.fetchReport(
                Mockito.anyString(),
                Mockito.any(UserNotification.class)
        )).thenReturn(report);

        assertThrows(ResourcesNotFound.class, () ->
                fetchReportDataService.GetReportData(
                        "101", "NGO-1", userNotification
                )
        );

        Mockito.verify(muncipalRepo, Mockito.never()) .save(Mockito.any());;
    }

    @Test
    void getReportData_Serversideproblem() {

        UserNotification userNotification = new UserNotification();

        ReportEntity report = new ReportEntity();
        report.setName("Server Side Problem");

        Mockito.when(muncipalCallMicroservice.fetchReport(
                Mockito.anyString(),
                Mockito.any(UserNotification.class)
        )).thenReturn(report);

        assertThrows(ResourcesNotFound.class, () ->
                fetchReportDataService.GetReportData(
                        "101", "NGO-1", userNotification
                )
        );

        Mockito.verify(muncipalRepo, Mockito.never()) .save(Mockito.any());;
    }
}
