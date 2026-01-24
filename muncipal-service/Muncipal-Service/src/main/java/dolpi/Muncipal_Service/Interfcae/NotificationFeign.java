package dolpi.Muncipal_Service.Interfcae;

import dolpi.Muncipal_Service.DTO.Notification;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@FeignClient(name="CarePaws-Notification-Service",url = "http://localhost:8086")
public interface NotificationFeign {
    @GetMapping("/save/getify")
    public List<Notification> checkngomuncipalnotify(@RequestParam String notification);
}
