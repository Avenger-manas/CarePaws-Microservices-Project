package dolpi.NGO_SERVICE.Service;

import dolpi.NGO_SERVICE.Exception.GlobalExceptionHandler;
import dolpi.NGO_SERVICE.Exception.ResourcesNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dolpi.NGO_SERVICE.Dto.Notification;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class ResucueNotifyService {

    @Autowired
    private NgoCallMicroservice ngoCallMicroservice;

    public List<Notification> notifyservice(String submissioID){
        log.info("Enter Success Notify Service");
       List<Notification> list=ngoCallMicroservice.notificationfeign(submissioID);

        if(list.get(0).getNgoanmcplId().equals("NOT FOUND")){
          throw new ResourcesNotFound("NOT FOUND");
        }

        if(list.get(0).getNgoanmcplId().equals("Server Side Problem")){
            throw new ResourcesNotFound("Server Side Problem");
        }

        return list;
    }
}
