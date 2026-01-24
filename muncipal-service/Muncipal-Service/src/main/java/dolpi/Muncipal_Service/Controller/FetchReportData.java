package dolpi.Muncipal_Service.Controller;

import dolpi.Muncipal_Service.DTO.UserNotification;
import dolpi.Muncipal_Service.Exception.ResourcesNotFound;
import dolpi.Muncipal_Service.Service.FetchReportDataService;
import dolpi.Muncipal_Service.Service.MuncipalCallMicroservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/muncipal-register")
public class FetchReportData {

    @Autowired
    private FetchReportDataService fetchReportDataService;

    @PostMapping("getReport")
    public ResponseEntity<?> getReport(@RequestHeader("X-USERNAME") String username, @RequestHeader("X-ROLE") String roles, @RequestHeader(value = "X-GATEWAY", required = true) String gateway, @RequestParam String id, @RequestParam String ngomuncipalId, @RequestBody UserNotification userNotification) {
        if (!roles.equals("ROLE_MUNCIPAL")) {
            throw new ResourcesNotFound("Not Found");
        }

        return new ResponseEntity<>(fetchReportDataService.GetReportData(id,ngomuncipalId,userNotification), HttpStatus.OK);

    }
}
