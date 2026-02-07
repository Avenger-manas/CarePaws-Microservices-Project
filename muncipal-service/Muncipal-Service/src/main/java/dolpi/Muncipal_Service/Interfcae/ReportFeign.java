package dolpi.Muncipal_Service.Interfcae; // Yeh sahi package hai

import dolpi.Muncipal_Service.DTO.ReportEntity; // NGO wala nahi, Muncipal wala DTO
import dolpi.Muncipal_Service.DTO.UserNotification;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="NOTIFICATION-SERVICE") 
public interface ReportFeign {
    @PostMapping("/fetch/GetReport")
    public ReportEntity fetchreport(@RequestParam String id, @RequestBody UserNotification userNotification);
}
