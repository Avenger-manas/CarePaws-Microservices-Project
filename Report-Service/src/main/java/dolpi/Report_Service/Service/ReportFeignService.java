package dolpi.Report_Service.Service;

import dolpi.Report_Service.Dto.UserNotification;
import dolpi.Report_Service.Interface.NotificationFeign;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportFeignService {

    @Autowired
    private NotificationFeign notificationFeign;

    @Retry(name = "notificationRetry")
    //@TimeLimiter(name = "municipalTimeLimiter")
    @CircuitBreaker(name = " notificationCB", fallbackMethod = "DeleteNotification")
    public String deletenotification(String id){
        return notificationFeign.deletenotification(id);
    }

    //fall Back Method
    public String DeleteNotification(String id){
        return "Server is Down";
    }
}
