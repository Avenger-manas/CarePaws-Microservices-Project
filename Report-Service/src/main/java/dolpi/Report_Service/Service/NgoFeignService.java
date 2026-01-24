package dolpi.Report_Service.Service;

import dolpi.Report_Service.Dto.RegisterNgo;
import dolpi.Report_Service.Interface.NGOFeign;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class NgoFeignService {
    @Autowired
    private NGOFeign ngoFeign;

    @Retry(name = "ngoRetry")
    @CircuitBreaker(name = "ngoCB", fallbackMethod = "ngoFallback")
    public List<RegisterNgo>findNGO(String city) {
       return ngoFeign.getNGO(city);
    }

    //  Correct fallback
    public List<RegisterNgo> ngoFallback(
            String city, Throwable ex) {

        List<RegisterNgo>ngo=new ArrayList<>();
        log.error("NGO Service fail",ex);

        return ngo;
    }
}
