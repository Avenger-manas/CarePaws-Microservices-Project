package dolpi.Muncipal_Service.Interfcae;

import dolpi.Muncipal_Service.DTO.ReportEntity;
import dolpi.Muncipal_Service.DTO.UserNotification;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="NOTIFICATION-SERVICE",url = "http://localhost:8085")
public interface ReportFeign {
    @PostMapping("/fetch/GetReport")
    public ReportEntity fetchreport(@RequestParam String id, @RequestBody UserNotification userNotification);
}
