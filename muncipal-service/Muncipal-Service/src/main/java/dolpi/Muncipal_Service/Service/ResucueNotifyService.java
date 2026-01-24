package dolpi.Muncipal_Service.Service;

import dolpi.Muncipal_Service.DTO.Notification;
import dolpi.Muncipal_Service.Exception.ResourcesNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//This Notification For Notification Service Call
@Service
@Slf4j
public class ResucueNotifyService {
    @Autowired
    private MuncipalCallMicroservice muncipalCallMicroservice;

    public List<Notification> notifyservice(String submissioID){
        log.info("Enter Success Notify Service");
        List<Notification> list=muncipalCallMicroservice.notificationfeign(submissioID);

        if(list.get(0).getNgoanmcplId().equals("NOT FOUND")){
            throw new ResourcesNotFound("NOT FOUND");
        }

        if(list.get(0).getNgoanmcplId().equals("Server Side Problem")){
            throw new ResourcesNotFound("Server Side Problem");
        }

        return list;
    }
}
