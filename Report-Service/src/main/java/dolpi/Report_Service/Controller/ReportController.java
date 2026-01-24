package dolpi.Report_Service.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dolpi.Report_Service.Dto.ReportDTO;
import dolpi.Report_Service.Entity.ReportEntity;
import dolpi.Report_Service.Exception.ResourcesNotFound;
import dolpi.Report_Service.Repository.ReportRepository;
import dolpi.Report_Service.Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/report")
public class ReportController {
    //this is used to the report near by ngo and muncipal organization

    @Autowired
    private ReportService reportService;

    @PostMapping("/complaint")
    public ResponseEntity<?> reportngomuncipal(@RequestHeader("X-USERNAME") String username, @RequestHeader("X-ROLE") String roles,@RequestHeader(value = "X-GATEWAY", required = true) String gateway,@RequestParam("report") String reportdto, @RequestParam("File") MultipartFile File)throws IOException {
        if (!roles.equals("ROLE_USER")) {
            throw new ResourcesNotFound("Not Found");
        }

        //json string convert reportDTO

        ObjectMapper mapper=new ObjectMapper();
        ReportDTO reportDTO=mapper.readValue(reportdto,ReportDTO.class);




        return new ResponseEntity<>(reportService.report(reportDTO,File),HttpStatus.OK);

    }


}
