package dolpi.NGO_SERVICE.Interface;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dolpi.NGO_SERVICE.Dto.Notification;

import java.util.List;

@FeignClient(name="CarePaws-Notification-Service",url = "http://localhost:8086")
public interface NotificationFeign {
    @GetMapping("/save/getify")
    public List<Notification> checkngomuncipalnotify(@RequestParam String notification);
}
