package dolpi.Report_Service.Service;

import dolpi.Report_Service.Dto.Notifiaction;
import dolpi.Report_Service.Interface.NotificationFeign;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class NotificationFeignService {
    @Autowired
    private NotificationFeign notificationFeign;

    @Retry(name = "ngoRetry")
   // @TimeLimiter(name = "ngoTimeLimiter")
    @CircuitBreaker(name = "ngoCB", fallbackMethod = "notificationFallback")
    public void savenotifiaction(Notifiaction notifiaction) {
            notificationFeign.savenotification(notifiaction);

    }

    // fallback for async void
    public void notificationFallback(Notifiaction notifiaction, Throwable ex) {
        log.warn(notifiaction.getSubmissionId(), ex.getMessage());
        log.error("Notification save failed for id -->");
    }


}
