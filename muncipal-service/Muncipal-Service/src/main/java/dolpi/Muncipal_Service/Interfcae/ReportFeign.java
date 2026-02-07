package dolpi.NGO_SERVICE.Interface;

import dolpi.NGO_SERVICE.Dto.ReportEntity;
import dolpi.NGO_SERVICE.Dto.UserNotification;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name="NOTIFICATION-SERVICE") 
public interface ReportFeign {
    @PostMapping("/fetch/GetReport")
    public ReportEntity fetchreport(@RequestParam String id, @RequestBody UserNotification userNotification);
}
