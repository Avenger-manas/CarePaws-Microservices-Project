package dolpi.Muncipal_Service.Service;

import dolpi.Muncipal_Service.DTO.ReportEntity;
import dolpi.Muncipal_Service.DTO.UserNotification;
import dolpi.Muncipal_Service.Entity.MuncipalNotification;
import dolpi.Muncipal_Service.Exception.ResourcesNotFound;
import dolpi.Muncipal_Service.Repository.MuncipalRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FetchReportDataService {
    //This Fetch The Report From Report Microservice
    @Autowired
    private MuncipalRepo muncipalRepo;

    @Autowired
    private MuncipalCallMicroservice muncipalCallMicroservice;

    public ReportEntity GetReportData(String id, String ngoId, UserNotification userNotification) {
        ReportEntity report=muncipalCallMicroservice.fetchReport(id,userNotification);


        if(report.getName().equals("NOT FOUND")){
            log.warn("NOT FOUND FETCHREPORTDATASERVICE NO DATA");
            throw new ResourcesNotFound("NOT FOUND");
        }

        if(report.getName().equals("Server Side Problem")){
            log.warn("SEVER SIDE PROBLEM");
            throw new ResourcesNotFound("Server Side Problem");
        }


        //this save notification for ngo
        log.info("save user notification");
        MuncipalNotification notification=new MuncipalNotification();
        notification.setNgoId(ngoId);
        notification.setSubmissionId(id);
        muncipalRepo.save(notification);

        return report;
    }
}
