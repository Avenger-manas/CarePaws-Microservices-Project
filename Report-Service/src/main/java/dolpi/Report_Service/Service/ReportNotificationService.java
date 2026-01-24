package dolpi.Report_Service.Service;

import dolpi.Report_Service.Exception.ResourcesNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReportNotificationService {

    @Autowired
    private ReportFeignService reportFeignService;

    public void deletenotificationdata(String id){
        String data=reportFeignService.deletenotification(id);

        if(data.equals("Server is Down")){
            log.warn("server down ReportNotificationService");
            throw new ResourcesNotFound("Server is Down");
        }
    }
}
