package dolpi.NGO_SERVICE.Service;

import dolpi.NGO_SERVICE.Dto.ReportEntity;
import dolpi.NGO_SERVICE.Dto.UserNotification;
import dolpi.NGO_SERVICE.Entity.NgoNotification;
import dolpi.NGO_SERVICE.Exception.ResourcesNotFound;
import dolpi.NGO_SERVICE.Repository.NgoNotificationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//This Service Fetch Data Report Service
@Service
public class FetchReportDataService {
    @Autowired
    private NgoCallMicroservice ngoCallMicroservice;

    @Autowired
    private NgoNotificationRepo notificationRepo;

    public ReportEntity reportdata(String id, String ngoId, UserNotification userNotification){
        ReportEntity report=ngoCallMicroservice.fetchReport(id,userNotification);

        if(report.getName().equals("NOT FOUND")){
            throw new ResourcesNotFound("NOT FOUND");
        }

        if(report.getName().equals("Server Side Problem")){
            throw new ResourcesNotFound("Server Side Problem");
        }


       //this save notification for ngo
        NgoNotification notification=new NgoNotification();
        notification.setNgoId(ngoId);
        notification.setSubmissionId(id);
        notificationRepo.save(notification);


        return report;
    }
}
