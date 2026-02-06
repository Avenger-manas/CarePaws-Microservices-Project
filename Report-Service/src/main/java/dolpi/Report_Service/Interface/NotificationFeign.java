package dolpi.Report_Service.Interface;

import dolpi.Report_Service.Dto.Notifiaction;
import dolpi.Report_Service.Dto.UserNotification;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="CAREPAWS-NOTIFICATION-SERVICE")
public interface NotificationFeign {
    @PostMapping("/save/save-notification")
    void savenotification(@RequestBody Notifiaction notification);

    @PostMapping("/save/userNotification")
    String saveusernotification(@RequestBody UserNotification userNotification);

    @PostMapping("/save/delete")
    String deletenotification(@RequestParam String submissionid);
}
