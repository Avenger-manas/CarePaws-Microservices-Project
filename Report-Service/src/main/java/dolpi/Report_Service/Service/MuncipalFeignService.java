package dolpi.Report_Service.Service;

import dolpi.Report_Service.Dto.RegisterEntity;
import dolpi.Report_Service.Interface.MuncipalFeign;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class MuncipalFeignService {
    @Autowired
    private MuncipalFeign muncipalFeign;

    @Retry(name = "municipalRetry")
    //@TimeLimiter(name = "municipalTimeLimiter")
    @CircuitBreaker(name = "municipalCB", fallbackMethod = "municipalFallback")
    public List<RegisterEntity> findMunicipal(String city) {
       return muncipalFeign.getmunciapl(city);
    }

    // Correct fallback
    public List<RegisterEntity> municipalFallback(
            String city, Throwable ex) {

        List<RegisterEntity>l=new ArrayList<>();
        log.error("MUNCIPAL SERVICE FAILD",ex);
        return  l;
    }
}
