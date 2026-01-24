package dolpi.Muncipal_Service.Service;

import dolpi.Muncipal_Service.DTO.Notification;
import dolpi.Muncipal_Service.DTO.ReportEntity;
import dolpi.Muncipal_Service.DTO.UserNotification;
import dolpi.Muncipal_Service.Exception.ResourcesNotFound;
import dolpi.Muncipal_Service.Interfcae.NotificationFeign;
import dolpi.Muncipal_Service.Interfcae.ReportFeign;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


import static org.bson.assertions.Assertions.assertNotNull;
import static org.junit.Assert.assertEquals;
import static reactor.core.publisher.Mono.when;
import static reactor.retry.Repeat.times;

@Service
@Slf4j
public class MuncipalCallMicroservice {
    @Autowired
    private ReportFeign reportFeign;

    @Autowired
    private NotificationFeign notificationFeign;


    // ===================== Notification =====================
    //This Is Notification
    @Retry(name = "muncipalRetry")
    //@TimeLimiter(name = "ngoTimeLimiter")
    @CircuitBreaker(name = "muncipalCB", fallbackMethod = "muncipalFallback")
    public List<Notification> notificationfeign(String notifyid){
        List<Notification> n=notificationFeign.checkngomuncipalnotify(notifyid);
        if(n.get(0).getNgoanmcplId().equals("NOT FOUND")){
            throw new ResourcesNotFound("NOT FOUND");
        }
        log.info("Succes NgoCallMicroservice");
        return n;
    }


    //FallBack Method
    public List<Notification> muncipalFallback(String notifyid, Throwable t){
        log.error("Server Side Problem"+t);
        Notification n = new Notification();
        n.setNgoanmcplId("Server Side Problem");
        n.setSubmissionId("NOT FOUND");
        List<Notification> list=new ArrayList<>();
        list.add(n);
        return list;
    }



    @Retry(name = "muncipalRetry")
    //@TimeLimiter(name = "ngoTimeLimiter")
    @CircuitBreaker(name = "muncipalCB", fallbackMethod = "ReportFallBack")
    public ReportEntity fetchReport(String id, UserNotification userNotification){
        ReportEntity report=reportFeign.fetchreport(id,userNotification);
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
