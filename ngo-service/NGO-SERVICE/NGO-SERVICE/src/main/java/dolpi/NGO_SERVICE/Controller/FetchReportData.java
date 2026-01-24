package dolpi.NGO_SERVICE.Controller;


import dolpi.NGO_SERVICE.Dto.ReportEntity;
import dolpi.NGO_SERVICE.Dto.UserNotification;
import dolpi.NGO_SERVICE.Exception.ResourcesNotFound;
import dolpi.NGO_SERVICE.Service.FetchReportDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

//This Controller fetch report data on the report microservice
@RestController
@RequestMapping("/ngo")
public class FetchReportData {

    @Autowired
    private FetchReportDataService fetchReportDataService;

    @PostMapping("/getReport")
    public ResponseEntity<?> getReport(@RequestHeader("X-USERNAME") String username, @RequestHeader("X-ROLE") String roles, @RequestHeader(value = "X-GATEWAY", required = true) String gateway, @RequestParam String id, @RequestParam String ngoId, @RequestBody UserNotification userNotification){
        if (!roles.equals("ROLE_NGO")) {
            throw new ResourcesNotFound("Not Found");
        }

        if(userNotification.getNGOanMNCPL_Name()==null) return new ResponseEntity<>("fail", HttpStatus.OK);

        return new ResponseEntity<>(fetchReportDataService.reportdata(id,ngoId,userNotification), HttpStatus.OK);
    }
}
