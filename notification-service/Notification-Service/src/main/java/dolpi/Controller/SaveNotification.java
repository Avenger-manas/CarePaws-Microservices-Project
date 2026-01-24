package dolpi.Controller;

import dolpi.Entity.Notification;
import dolpi.Entity.UserNotification;
import dolpi.Repository.NotificationRepo;
import dolpi.Repository.UserNotificationRepo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/save")
@Slf4j
public class SaveNotification {
    @Autowired
    private NotificationRepo notificationRepo;

    @Autowired
    private UserNotificationRepo userNotificationRepo;

    //save ngo and mucipal info
    @PostMapping("/save-notification")
    public void findngocity(@RequestBody Notification notification){
        notificationRepo.save(notification);
    }

    //this controller get notifiacation data
    @GetMapping("/getify")
    public List<Notification> getNotify(@RequestParam String notification){
        List<Notification>list=notificationRepo.findByNgoanmcplId(notification);
          if(list!=null && !list.isEmpty()){
              log.info("succes fetch id");
             return list;
          }
          log.warn("Repository Is Empty");

          Notification n=new Notification();
          n.setNgoanmcplId("NOT FOUND");
          list.add(n);
          return list;
    }

    //save user notification data
    @PostMapping("/userNotification")
    public String saveusernotification(@RequestBody UserNotification userNotification){
        userNotificationRepo.save(userNotification);
        return "Succeafully insert";
    }


    //delete notification data
    @PostMapping("/delete")
    public String deletenotification(@RequestParam String submissionid){
        notificationRepo.deleteBySubmissionId(submissionid);
        return "Succesfully deleted";
    }


}
