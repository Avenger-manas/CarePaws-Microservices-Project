package dolpi.Report_Service.Service;

import dolpi.Report_Service.Dto.UserNotification;
import dolpi.Report_Service.Exception.ResourcesNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserNotificationService {
    //This Service is save notification
    @Autowired
    private UserNoticationFeignService userNoticationFeignService;

    public String Notificationuser(UserNotification userNotification){
        String promt=userNoticationFeignService.UserNotify(userNotification);
        if(promt.equals("Server is Down")){
            throw new ResourcesNotFound("Server is DOWN");
        }

        log.info("succes usernotification");

        return promt;
    }
}
