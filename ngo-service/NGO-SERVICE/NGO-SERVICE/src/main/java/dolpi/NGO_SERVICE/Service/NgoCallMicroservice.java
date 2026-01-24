package dolpi.NGO_SERVICE.Service;

import dolpi.NGO_SERVICE.Dto.ReportEntity;
import dolpi.NGO_SERVICE.Dto.UserNotification;
import dolpi.NGO_SERVICE.Exception.ResourcesNotFound;
import dolpi.NGO_SERVICE.Interface.NotificationFeign;
import dolpi.NGO_SERVICE.Interface.ReortFeign;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import dolpi.NGO_SERVICE.Dto.Notification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class NgoCallMicroservice {
    @Autowired
    private NotificationFeign notificationFeign;

    @Autowired
    private ReortFeign reortFeign;

    @Retry(name = "ngoRetry")
     //@TimeLimiter(name = "ngoTimeLimiter")
    @CircuitBreaker(name = "ngoCB", fallbackMethod = "ngoFallback")
    public List<Notification> notificationfeign(String notifyid){
         List<Notification> n=notificationFeign.checkngomuncipalnotify(notifyid);
         if(n.get(0).getNgoanmcplId().equals("NOT FOUND")){
             throw new ResourcesNotFound("NOT FOUND");
         }
        log.info("Succes NgoCallMicroservice");
        return n;
    }


    //FallBack Method
    public List<Notification> ngoFallback(String notifyid, Throwable t){
        log.error("Server Side Problem"+t);
        Notification n = new Notification();
        n.setNgoanmcplId("Server Side Problem");
        n.setSubmissionId("NOT FOUND");
        List<Notification> list=new ArrayList<>();
        list.add(n);
        return list;
    }


    //This Fetch Report Data From Report Microservice

    @Retry(name = "ngoRetry")
    //@TimeLimiter(name = "ngoTimeLimiter")
    @CircuitBreaker(name = "ngoCB", fallbackMethod = "ReportFallBack")
    public ReportEntity fetchReport(String id, UserNotification userNotification){
        ReportEntity report=reortFeign.fetchreport(id,userNotification);
        if(report.getName().equals("NOT FOUND")){
            throw new ResourcesNotFound("NOT FOUND");
        }
        log.info("Succes fetch Report data");
        return report;
    }

    //This is Report Fall Back
    public ReportEntity ReportFallBack(String id,UserNotification userNotification, Throwable t){
        log.error("Server Side Problem",t);
        ReportEntity report=new ReportEntity();
        report.setName("Server Side Problem");
        return report;
    }


}
