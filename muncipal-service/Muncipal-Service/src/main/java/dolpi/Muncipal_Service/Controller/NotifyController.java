package dolpi.Muncipal_Service.Controller;

import dolpi.Muncipal_Service.Exception.ResourcesNotFound;
import dolpi.Muncipal_Service.Service.ResucueNotifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

//This Controller is Get Notification
@RequestMapping("/muncipal-register")
@RestController
@Slf4j
public class NotifyController {

    @Autowired
    private ResucueNotifyService resucueNotifyService;

    @GetMapping("/notify")
    public ResponseEntity<?> Notify(@RequestHeader("X-USERNAME") String username, @RequestHeader("X-ROLE") String roles, @RequestHeader(value = "X-GATEWAY", required = true) String gateway, @RequestParam String ngomnplId){
        if (!roles.equals("ROLE_MUNCIPAL")) {
            throw new ResourcesNotFound("Not Found");
        }

        log.info("Notfy Controller Is RUN");
        return new ResponseEntity<>(resucueNotifyService.notifyservice(ngomnplId), HttpStatus.OK);

    }
}
