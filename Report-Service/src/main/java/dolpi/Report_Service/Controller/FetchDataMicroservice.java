package dolpi.Report_Service.Controller;

import dolpi.Report_Service.Dto.UserNotification;
import dolpi.Report_Service.Entity.ReportEntity;
import dolpi.Report_Service.Repository.ReportRepository;
import dolpi.Report_Service.Service.ReportNotificationService;
import dolpi.Report_Service.Service.UserNoticationFeignService;
import dolpi.Report_Service.Service.UserNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

//This Controller Call Microservice
@RestController
@RequestMapping("/fetch")
@Slf4j
public class FetchDataMicroservice {
    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserNotificationService userNotificationService;

    @Autowired
    private ReportNotificationService reportNotificationService;



    @PostMapping("/GetReport")
    public ReportEntity fetchReport(@RequestParam String id, @RequestBody UserNotification userNotification){
        Optional<ReportEntity> r=reportRepository.findById(id);
        ReportEntity report=new ReportEntity();
        if(r.isPresent()){

            userNotification.setUserid(id);
           userNotificationService.Notificationuser(userNotification);
           reportNotificationService.deletenotificationdata(id);
           report=r.get();
            log.info("success given report");
           return report;

        }
        log.warn("not foudn problem report not given");
        report.setName("NOT FOUND");
        report.setDescription("NOT NULL");
        return report;
    }
}
