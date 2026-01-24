package dolpi.Report_Service.Service;

import dolpi.Report_Service.Dto.UserNotification;
import dolpi.Report_Service.Interface.NotificationFeign;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserNoticationFeignService {
    @Autowired
    private NotificationFeign notificationFeign;

    @Retry(name = "notificationRetry")
    //@TimeLimiter(name = "municipalTimeLimiter")
    @CircuitBreaker(name = " notificationCB", fallbackMethod = "UserNotification")
    public String UserNotify(UserNotification userNotification){
        return notificationFeign.saveusernotification(userNotification);
    }

    //fall Back Method
    public String UserNotification(UserNotification userNotification,Throwable ex){
        return "Server is Down";
    }

}
